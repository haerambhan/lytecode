function signup(){
	
	var userId = document.getElementById('userId');
	var userName = document.getElementById('name');
	var pass1 = document.getElementById('pass1');
	var pass2 = document.getElementById('pass2');
	
	var data = getSignUpObject(userId.value, userName.value, pass1.value, pass2.value);
	if(data){
		callAPI('signup', 'post', data, handleAuthResponse);
	}
}

function getSignUpObject(userId, userName, pass1, pass2) {
	var data = {
		userId: '',
		userName: '',
		password: ''
	};
	if (userId === '') {
		warn('userId required');
		return null;
	}
	if (userName === '') {
		warn('Name required');
		return null;
	}
	if (pass1 === '' || pass2 === '') {
		warn('Password required');
		return null;
	}
	if (pass1 !== pass2) {
		warn('Passwords do not match');
		return null;
	}
	data.userId = userId.trim();
	data.userName = userName.trim();
	data.password = pass2;
	return data
}
