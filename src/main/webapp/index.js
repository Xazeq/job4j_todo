function validate() {
    if ($('#desc').val() === '') {
        alert($('#desc').attr('title'));
        return false;
    }
    return true;
}

function getItems() {
    let check = document.getElementById('checkbox');
    let username = window.localStorage.getItem('user');
    document.getElementById('exit').innerText = username + ' | Выход';
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todo/index.do',
        dataType: 'json',
        data: {
            checkbox: check.checked,
            username : username
        }
    }).done(function (data) {
        $('#tableBodyId tr').remove();
        let table = document.getElementById('table');
        for (let i = 0; i < data.length; i++) {
            let item = data[i];
            let id = item['id'];
            let desc = item['description'];
            let done = item['isDone'];
            let status;
            let button;
            let categories = item['categories'];
            let itemCat = "";
            categories.forEach(function(cat) {
                itemCat += cat.name + '<br/>';
            });
            if (done === true) {
                button = '';
                status = '<i class="fa fa-check-circle-o fa-2x" style="color:green"></i>'
            } else {
                button = '<button type="button" class="btn btn-primary" onClick="closeTask('+id+')">Завершить</button>'
                status = '<i class="fa fa-circle-o fa-2x" style="color:red"></i>'
            }
            $('#table tbody').append('<tr><td>' + desc + '</td>'
                + '<td style="text-align: center">' + itemCat + '</td>'
                + '<td style="text-align: center">' + status + '</td>'
                + '<td>' + button + '</td></tr>');
        }
    }).fail(function (err) {
        console.log(err);
    });
}

function addItem() {
    if (!validate()) {
        return false;
    }
    let username = window.localStorage.getItem('user');
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/index.do',
        data: {
            description : $('#desc').val(),
            username : username,
            categories : $('#cIds').val()
        },
        dataType: 'text'
    }).done(function (data) {
        if (data === '400') {
            alert('Выберите категорию!');
        } else {
            getItems();
        }
    }).fail(function (err) {
        console.log(err);
    });
}

function closeTask(id) {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/todo/closetask.do',
        data: {
            id : id
        }
    }).done(function () {
        getItems();
    }).fail(function (err) {
        console.log(err);
    });
}

function getCategories() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/todo/category.do',
        dataType: 'json'
    }).done(function (data) {
        let select = document.getElementById('cIds');
        for (let i = 0; i < data.length; i++) {
            let cat = data[i];
            $('#cIds').append($('<option>', {
                value: cat.id,
                text: cat.name,
            }));
        }
    }).fail(function (err) {
        console.log(err);
    });
}
