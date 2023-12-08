$(document).ready(function () {
    getAllUsers();
});

let roles = [
    {name: "ROLE_ADMIN", authority: "ROLE_ADMIN", id: 1},
    {name: "ROLE_USER", authority: "ROLE_USER", id: 2}
];
let rolesList;



function getAllUsers() {
    const usersTable = $('#usertbody');
    usersTable.empty()
    fetch("http://localhost:8080/admin/users")
        .then(response => response.json())
        .then(users => {
            users.forEach(user => {
                let row = `$(
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.name}</td>
                        <td>${user.surname}</td>
                        <td>${user.getRolesNames}</td>
                        <td>
                            <button type="button" class="btn btn-info text-white" data-bs-toggle="modal"
                            onclick="openEditModal(${user.id})">Edit</button>
                        </td>
                        <td>
                            <button type="button" class="btn btn-danger" data-bs-toggle="modal" 
                            onclick="openDeleteModal(${user.id})">Delete</button>
                        </td>
                    </tr>
                )`
                usersTable.append(row)
            })
        })
        .catch(err => console.log(err))
}

function addUser() {
    let newUserForm = $('#newuserform')[0];
    newUserForm.onsubmit = function (event) {
        event.preventDefault()
        event.stopPropagation()
        let newUser = JSON.stringify({
            username: $(`[name="username"]`, newUserForm).val(),
            name: $(`[name="name"]`, newUserForm).val(),
            password: $(`[name="password"]`, newUserForm).val(),
            surname: $(`[name="surname"]`, newUserForm).val(),
            roles: getRole(newUserForm)
        })
        fetch("http://localhost:8080/admin/users", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: newUser
        }).then(r => {
            newUserForm.reset()
            if (!r.ok) {
                alert("Error while creating user")
            } else {
                $('#tab1')[0].click()
            }
        })
    }
}
function openEditModal(id) {
    let modal = new bootstrap.Modal($('#editmodal'));
    let form = $('#editmodalform')[0];
    openModal(modal,form,id);
    form.onsubmit = function (event) {
        event.preventDefault()
        event.stopPropagation()
        let updateUser = JSON.stringify({
            id: $(`[name="id"]`, form).val(),
            username: $(`[name="username"]`, form).val(),
            name: $(`[name="name"]`, form).val(),
            password: $(`[name="password"]`, form).val(),
            surname: $(`[name="surname"]`, form).val(),
            roles: getRole(form)
        })
        fetch("http://localhost:8080/admin/users", {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: updateUser
        }).then(r => {
            form.reset()
            modal.hide()
            if (!r.ok) {
                alert("Error while updating user")
            } else {
                $('#tab1')[0].click()
            }
        })
    }
}
function openDeleteModal(id) {
    let modal = new bootstrap.Modal($('#deletemodal'));
    let form = $('#deletemodalform')[0];
    let params = new URLSearchParams();
    params.append("id",id);
    openModal(modal,form,id);
    form.onsubmit = function (event) {
        event.preventDefault()
        event.stopPropagation()
        fetch("http://localhost:8080/admin/users?" + params.toString(), {
            method: 'DELETE',
        }).then(r => {
            form.reset()
            modal.hide()
            if (!r.ok) {
                alert("Error while updating user")
            } else {
                $('#tab1')[0].click()
            }
        })
    }
}
function openModal(modal, form, id) {
    let idparam = new URLSearchParams()
    idparam.append("id", id);
    fetch("http://localhost:8080/admin/user?" + idparam.toString())
        .then(response =>
            response.json()).then(user => {
        $(`[name="id"]`, form).val(user.id),
            $(`[name="username"]`, form).val(user.username),
            $(`[name="name"]`, form).val(user.name),
            $(`[name="surname"]`, form).val(user.surname)
    })
    modal.show();
}




function getRole(form) {
    let role = []
    let options = $(`[name="roles"]`, form)[0].options
    for (let i = 0; i < 2; i++) {
        if (options[i].selected) {
            role.push(roles[i])
        }
    }
    return role
}

