function loadTests() {
	callAPI('tests', 'get', null, function(response){		
		if(response.code === 2000){
			var data = response.body;
			if (data.length === 0) {
				throw 'No questions found';
			}
			data.forEach(question => {
				var ques = document.createElement('div');
				ques.setAttribute('id', 'qs');
				ques.setAttribute('onclick', `redirect(${question.testId})`);
				var title = document.createElement('h5');
				var difficulty = document.createElement('span');
				title.innerHTML = question.testTitle;
				difficulty.innerHTML = question.testDiff;
				ques.appendChild(title);
				ques.appendChild(difficulty);
				document.getElementById('questions').appendChild(ques);
			});	
		}
		else{
			throw response.message;
		}
	});
};

function redirect(testId) {
	localStorage.setItem('testId', testId);
	window.location.href = 'userTest.html';
}