function warn(details) {
  var warning = document.getElementById('warning');
  warning.innerHTML = details;
}

function notify(details) {
  var notification = document.getElementById('notification');
  notification.innerHTML = details;
}

function loadName(){
	document.getElementById('name').innerHTML = 'Welcome ' + sessionStorage.getItem("userName");
}

function logout(){
	callAPI('logout', 'post', null, function(response){
		sessionStorage.clear();
		if(response.code === 2000){
			window.location.href = 'index.html';
			return;				
		}
		else{
			throw response.message;
		}
	})
}

function getProjectName(){
	return "lytecode";
}

function callAPI(api, method, body, responseHandler){
	var reqObj = {
		method: method,
		headers: { 'content-type': 'application/json' },
	}
	if(body){
		reqObj.body = JSON.stringify(body);
	}
	fetch('/'+ getProjectName() + '/api/' + api, reqObj)
	.then((res) => {
		if (res.status != 200) {
			throw 'Application error';
		}
		return res.json();
	})
	.then((response) => {
		responseHandler(response);
	})
	.catch((error) => {
		warn(error);
	});
}

function handleAuthResponse(response){
	if(response.code === 2000){
		sessionStorage.setItem("userId", response.body.userId);
		sessionStorage.setItem("userName", response.body.userName);
		sessionStorage.setItem("userType", response.body.userType);
		if(response.body.userType == 1){
			window.location.href = 'ui/createTest.html';
			return;				
		}
		else{
			window.location.href = 'ui/userHome.html';
			return;
		}
	}
	else{
		throw response.message;			
	}
}
