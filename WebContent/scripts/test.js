document.getElementById('editor').style.fontSize = '12px';
var editor = ace.edit('editor', {
	theme: 'ace/theme/tomorrow_night_blue',
	mode: 'ace/mode/java',
	maxLines: 30,
	minLines: 15,
	wrap: true,
	autoScrollEditorIntoView: true,
});

var publicTc = {};
var testcases = [];

function loadTest() {
	var testId = localStorage.getItem('testId');
	callAPI('tests/' + testId, 'get', null, function(response){		
		if(response.code === 2000){
			var data = response.body;
			publicTc = data.publicTc;
			testcases = data.testcases;
			document.getElementById('title').innerHTML = data.testTitle;
			document.getElementById('description').innerHTML = data.testDesc;
			document
				.querySelectorAll('#ip')
				.forEach((ele) => (ele.innerHTML = data.publicTc.input));
			document
				.querySelectorAll('#op')
				.forEach((ele) => (ele.innerHTML = data.publicTc.output));
			return;				
		}
		else{
			throw response.message;
		}
	});
};

function changeLang(lang) {
	editor.session.setMode('ace/mode/' + lang.value);
}

async function execute() {
	document.querySelector('.loader').style.display = 'block';
	document.querySelectorAll('.hidden').forEach((ele) => (ele.style.display = 'none'));
	document.getElementById('sop').style.display = 'block';
	document.getElementById('sop').style.color = 'red';
	document.getElementById('sop').innerHTML = 'Evaluating public test case...';
	var result = await run(publicTc.input);
	if (result.trim() == publicTc.output.trim()) {
		document.getElementById('sop').style.color = 'green';
		document.getElementById('sop').innerHTML = 'Public test case passed...Evaluating hidden testcases...';
		var count = 0;
		var num = 0;
		for (testcase of testcases) {
			num++;
			var op = await run(testcase.input);
			if (op.trim() === testcase.output.trim()) count++;

			if (num === testcases.length) {
				if (count === num) {
					document.getElementById('sop').style.color = 'green';
				} else {
					document.getElementById('sop').style.color = 'red';
				}
				document.getElementById('sop').style.display = 'block';
				document.getElementById('sop').innerHTML = `${count} out of ${testcases.length} Hidden test cases passed`;
				document.querySelector('.loader').style.display = 'none';
			}
		}
	} else {
		document.querySelector('.loader').style.display = 'none';
		document.querySelectorAll('.hidden').forEach((ele) => (ele.style.display = 'block'));
		document.getElementById('sop').innerHTML = result;
	}
}

async function run(input) {
	document.getElementById('mynav').disabled = true;
	var req = {
		clientId: 'deaf2972da2610ab080b5ff9f179ed5a',
		clientSecret: '9ec2445f49c54dc9b12c579959610c0ccfa6cc0f7233e687bf71766c2110b93d',
		stdin: input,
		script: '',
		language: '',
		versionIndex: '3',
	};

	req.script = editor.getValue();
	req.language = document.getElementById('lang').value;

	return fetch('https://ancient-eyrie-49794.herokuapp.com/', {
		method: 'post',
		headers: { 'content-type': 'application/json' },
		body: JSON.stringify(req),
	})
	.then((res) => res.json())
	.then((data) => {
		document.getElementById('mynav').disabled = false;
		return data.output;
	});
}
function submit() {
	// Yet to implement
}