function validate() {
    if ($('#username').val() === '') {
        alert($('#username').attr('title'));
        return false;
    } else if ($('#password').val() === '') {
        alert($('#password').attr('title'));
        return false;
    }
    return true;
}

function checkUser() {
    if (!validate()) {
        return false;
    }
    let username = $('#username').val();
    let password = $('#password').val();
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/login.do',
        data: {
            username : username,
            password : password
        },
        dataType: 'text'
    }).done(function (data) {
        if (data === "200") {
            localStorage.setItem("user", username);
            window.location.href = "http://localhost:8080/todo/index.html";
        } else {
            alert("Неверный логин или пароль");
        }
    }).fail(function (err) {
       console.log(err);
    });
}