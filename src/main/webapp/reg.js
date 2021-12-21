function validate() {
    if ($('#username').val() === '') {
        alert($('#username').attr('title'));
        return false;
    } else if ($('#email').val() === '') {
        alert($('#email').attr('title'));
        return false;
    } else if ($('#password').val() === '') {
        alert($('#password').attr('title'));
        return false;
    }
    return true;
}

function register() {
    if (!validate()) {
        return false;
    }
    let username = $('#username').val();
    let email = $('#email').val();
    let password = $('#password').val();
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/reg.do',
        data: {
            username : username,
            email : email,
            password : password
        },
        dataType: 'text'
    }).done(function (data) {
        if (data === "400") {
            alert("Пользователь с таким email или логином уже зарегистрирован");
        } else {
            localStorage.setItem("user", username);
            window.location.href = "http://localhost:8080/todo/index.html";
        }
    }).fail(function (err) {
        console.log(err);
    });
}