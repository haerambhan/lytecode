function warn(details) {
  var warning = document.getElementById('warning');
  warning.innerHTML = details;
}

function notify(details) {
  var notification = document.getElementById('notification');
  notification.innerHTML = details;
}

function getSession() {
  fetch('http://localhost:8080/SmartEditor/Session')
    .then(res => {
      return res.json();
    })
    .then(data => {
      if (data === null) {
        window.location.href = '../index.html';
        return;
      } else {
        document.getElementById('name').innerHTML = 'Welcome ' + data.userName;
      }
      console.log(data);
    });
}
