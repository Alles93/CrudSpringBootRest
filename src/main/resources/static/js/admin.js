$(document).ready(function () {
    getUsers();
    addNewUser();

    function getUsers() {
        fetch("http://localhost:8080/rest/users/", {
            method: "GET"
        }).then(
            function (response) {
                response.json().then((data) => {
                    makeTable(data);
                });
            })

        function makeTable(data) {
            let wrapColumn = function (value) {
                return "<td>" + value + "</td>";
            };
            $("#usersTable tbody").append("<tr>" +
                "<th>ID</th>" +
                "<th>Username</th>" +
                "<th>Lastname</th>" +
                "<th>Email</th>" +
                "<th>Age</th>" +
                "<th>Role</th>" +
                "<th>Edit</th>" +
                "<th>Delete</th>" +
                "</tr>");
            for (let i = 0; i < data.length; i += 1) {
                let trId = data[i].id;
                let editButton = '<button type="button" class="btn btn-info eBtn">Edit</button>';
                let deleteButton = '<button type="button" class="btn btn-danger dBtn">Delete</button>';
                let user = data[i];
                let roles = data[i].roles.map((role) => role.name).join(', ');

                /* user.roles.forEach(role => roles.push(role.role))
                 console.log(user.roles.map((role)=>role.name).join(','))*/


                $("#usersTable tbody").append("<tr id=" + trId + ">" +
                    wrapColumn(data[i].id) +
                    wrapColumn(data[i].username) +
                    wrapColumn(data[i].lastname) +
                    wrapColumn(data[i].email) +
                    wrapColumn(data[i].age) +
                    wrapColumn(roles) +
                    wrapColumn(editButton) +
                    wrapColumn(deleteButton) +
                    "</tr>")
            }


            $('#usersTable .eBtn').on('click', function (event) {
                event.preventDefault();
                let trId = $(this).closest('tr').attr('id');
                let url = "http://localhost:8080/rest/users/" + trId
                fetch(url, {
                    method: 'GET'
                }).then((res) => res.json())
                    .then((data) => {
                        let userRoles = data.roles;
                        let roles = "";
                        //   let roles = data[i].roles.map((role) => role.name).join(', ');
                        for (let j = 0; j < userRoles.length; j++) {
                            roles += userRoles[j].role + " ";
                        }


                        $('.myForm #id').val(data.id);
                        $('.myForm #username').val(data.username);
                        $('.myForm #lastname').val(data.lastname);
                        $('.myForm #age').val(data.age);
                        $('.myForm #email').val(data.email);
                        $('.myForm #password').val(data.password);
                        /*        $('.myForm #userRoles').val(roles);
                                if (roles.includes('ROLE_USER')) {
                                    $('#USER').prop('checked', true);
                                }
                                if (roles.includes('ROLE_ADMIN')) {
                                    $('#ADMIN').prop('checked', true);
                                }*/
                    })

                $('.myForm #editModal').modal();
                console.log("Перешли")
                document.getElementById('editBtn').addEventListener('click', ev => {
                    ev.preventDefault();

                    console.log("Нажали")

                    const userData = $('#form').serializeArray().reduce(function (obj, item) {
                        if (item.name === 'roles')
                            obj[item.name] = [...obj[item.name], {
                                name: item.value,
                                id: item.value === 'ROLE_USER' ? 1 : 2,
                                users: null
                            }]
                        else {
                            obj[item.name] = item.value;
                        }
                        return obj;
                    }, {roles: []})

                    console.log(userData)


                    fetch(url, {
                        method: 'PUT',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(
                            userData
                        )
                    }).then(res => res.json())
                        .then(data => {
                            $('#usersTable tbody').empty();
                            getUsers();
                        })
                        .catch(function (error) {
                            console.log('Request failed', error);
                        });
                }, {once: true})
            });

            $('#usersTable .dBtn').on('click', function (event) {
                event.preventDefault();
                $('#delModal').modal();
                document.getElementById('delBtn').addEventListener('click', ev => {


                    ev.preventDefault();
                    let trId = $(this).closest('tr').attr('id');
                    let url = "http://localhost:8080/rest/users/" + trId
                    fetch(url, {
                        method: "DELETE"
                    }).catch(function (err) {
                        console.log('Fetch Error :-S', err);
                    });
                    $(this).parents('tr').remove();
                }, {once: true})
            });
        }
    }

    function addNewUser() {
        document.getElementById('newUserForm').addEventListener('submit', ev => {
            ev.preventDefault();
            const userData = $('#newUserForm').serializeArray().reduce(function (obj, item) {
                if (item.name === 'roles')
                    obj[item.name] = [...obj[item.name], {
                        name: item.value,
                        id: item.value === 'ROLE_USER' ? 1 : 2,
                        users: null
                    }]
                else {
                    obj[item.name] = item.value;
                }
                return obj;
            }, {roles: []})


            /*     if (roleAdmin.checked && roleUser.checked) {
                     rolesArr = roleAdmin.value, roleUser.value
                 }*/

            fetch("http://localhost:8080/rest/users/", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(
                    userData
                )
            }).then(res => res.json())
                .then(data => {
                    $('#usersTable tbody').empty();
                    getUsers();
                })
                .catch(function (error) {
                    console.log('Request failed', error);
                });
            formClear();
        });

        function formClear() {
            $("#newUsername").val("");
            $("#newLastname").val("");
            $("#newPassword").val("");
            $("#newEmail").val("");
            $("#newAge").val("");
            $('#newUserRole').prop('checked', false);
            $('#newAMINRole').prop('checked', false);
            console.log('clear form');
        }
    }
});
