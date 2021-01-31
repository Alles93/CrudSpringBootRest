$(document).ready(function () {
    let trId = $("div:first").attr("id");
    console.log(typeof trId)
    let url = "http://localhost:8080/rest/users/" + trId
    fetch(url, {
        method: "GET"
    }).then(
        function (response) {
            response.json().then((data) => {
                let wrapColumn = function (value) {
                    return "<td>" + value + "</td>";
                };
                $("#usersTable tbody").append("<tr>" +
                    "<th>ID</th>" +
                    "<th>Username</th>" +
                    "<th>LastName</th>" +
                    "<th>EMail</th>" +
                    "<th>Age</th>" +
                    "<th>Role</th>" +
                    "</tr>");

                let trId = data.id;
                let user = data;

                let roles = data.roles.map((role) => role.name).join(', ');

                $("#usersTable tbody").append("<tr id=" + trId + ">" +
                    wrapColumn(data.id) +
                    wrapColumn(data.username) +
                    wrapColumn(data.lastname) +
                    wrapColumn(data.email) +
                    wrapColumn(data.age) +
                    wrapColumn(roles) +
                    "</tr>")
            });
        }
    )
});