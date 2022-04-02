var editor = ace.edit('editor', {
	theme: 'ace/theme/tomorrow_night_blue',
	mode: 'ace/mode/java',
	maxLines: 30,
	minLines: 17,
	wrap: true,
	autoScrollEditorIntoView: true,
});

function loadTest() {
	document.getElementById('editor').style.fontSize = '14px';
	var testId = localStorage.getItem('testId');
	callAPI('tests/' + testId, 'get', null, function(response){		
		if(response.code === 2000){
			var data = response.body;
			document.getElementById('title').innerHTML = data.testTitle;
			document.getElementById('description').innerHTML = data.testDesc;
			document.querySelectorAll('#ip').forEach((ele) => (ele.innerHTML = data.publicTc.input));
			document.querySelectorAll('#op').forEach((ele) => (ele.innerHTML = data.publicTc.output));
			return;				
		}
		else{
			throw response.message;
		}
	});
};

function changeMode(lang) {
	var value = "";
	if(lang.value === "java"){
		value = "java"
	}
	if(lang.value === "python3"){
		value = "python";
	}
	if(lang.value === "c" || lang.value === "cpp"){
		value = "c_cpp";
	}
	editor.session.setMode('ace/mode/' + value);
}

function executeCode(mode, resultHandler){
	
	document.querySelector('.loader').style.display = 'block';
	document.querySelectorAll('.hidden').forEach((ele) => (ele.style.display = 'none'));
	document.getElementById('sop').style.display = 'block';
	document.getElementById('sop').style.color = 'red';
	document.getElementById('sop').innerHTML = mode === "run" ? 'Evaluating public test case..' : 'Evaluating hidden test cases..';
	var body = {
		script : editor.getValue(),
		language : document.getElementById('lang').value
	}
	var testId = localStorage.getItem('testId');
	callAPI('tests/' + testId + '/' + mode, 'post', body, function(response){		
		if(response.code === 2000){
			var result = response.body;
			document.querySelector('.loader').style.display = 'none';		
			resultHandler(result);
		}
		else{
			throw response.message;
		}
	});
}

function runHandler(result){
	if(result.obtained_op.trim() === result.expected_op.trim()){
		document.getElementById('sop').style.color = 'green';
		document.getElementById('sop').innerHTML = 'Public test case passed. Click submit to evluate hidden test cases';
	}
	else{
		document.querySelectorAll('.hidden').forEach((ele) => (ele.style.display = 'block'));
		document.getElementById('sop').innerHTML = result.obtained_op;
	}
}

function submitHandler(result){
	var passed = result.passedTestCases;
	var total = result.totalTestCases;
	if(passed == total){
		document.getElementById('sop').style.color = 'green';
	}
	document.getElementById('sop').innerHTML = `${passed} out of ${total} Hidden test cases passed`;
}
