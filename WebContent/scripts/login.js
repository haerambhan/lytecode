function login(){
	var userId = document.getElementById('userId');
	var password = document.getElementById('password');
	var data = getLoginObject(userId.value, password.value);
	if(data){
		callAPI('login', 'post', data, handleAuthResponse);
	}
}


function getLoginObject(userId, password) {
	var data = {
		userId: '',
		password: '',
	};
	if (userId === '') {
		warn('User ID required');
		return null;
	}
	if (password === '') {
		warn('Password required');
		return null;
	}
	data.userId = userId.trim();
	data.password = password;
	return data;
}