function createTest() {
	var data = getTestData();
	if (data) {
		callAPI('tests', 'post', data, function(response){		
			if(response.code === 2000){
				window.alert('Question Created successfully');
				window.location.reload();
				return;				
			}
			else{
				throw response.message;
			}
		});
	}
}

function getTestData() {
	var data = {
		title: '',
		description: '',
		difficulty: '',
		specialTc: '',
		publicTc: {
			ip: '',
			op: '',
		},
		testcases: [],
	};
	var title = document.getElementById('title');
	var description = document.getElementById('description');
	var difficulty = document.getElementById('difficulty');
	var ip = document.getElementById('ip');
	var op = document.getElementById('op');
	var ips = document.querySelectorAll('#Input');
	var ops = document.querySelectorAll('#Output');
	var count = 0;

	if (title.value === '' || description.value === '') {
		warn('Enter all required details.');
		return;
	}
	data.title = title.value.trim();
	data.description = description.value;
	data.difficulty = difficulty.value.trim();
	data.publicTc.ip = ip.value;
	data.publicTc.op = op.value;
	ips.forEach((ip) => {
		if (ip.value === '' || ops[count].value === '') return;
		data.testcases.push({
			input: JSON.stringify(ip.value),
			output: JSON.stringify(ops[count].value),
		});
		count++;
	});
	return data;
}

function addTestCase() {
	var span = document.createElement('span');
	span.innerHTML = '#1';
	var testcase = document.createElement('div');
	testcase.appendChild(span);
	testcase.setAttribute('id', 'testcases');
	var static = ['Input', 'Output'];
	static.forEach((type) => {
		testcase.appendChild(addBox(type));
	});
	document.getElementById('tc').appendChild(testcase);
}

function addBox(type) {
	var Box = document.createElement('div');
	Box.setAttribute('class', 'form-group');
	var textarea = document.createElement('textarea');
	textarea.setAttribute('class', 'form-control ');
	textarea.setAttribute('rows', '3');
	textarea.setAttribute('id', type);
	textarea.setAttribute('placeholder', `Enter ${type}`);
	Box.appendChild(textarea);
	return Box;
}

