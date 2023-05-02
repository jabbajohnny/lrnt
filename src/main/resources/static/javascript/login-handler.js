document.getElementById("signup-form").addEventListener("submit", function (event) {
    event.preventDefault();
    registerUser();
});

document.getElementById("login-form").addEventListener("submit", function(event) {
    event.preventDefault()
    loginUser();
});

window.onload = function () {
    let guestContent = document.getElementById("guest-content");
    let userContent = document.getElementById("user-content");
}


function registerUser() {
    const form = document.getElementById("signup-form");
    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());
    const errorMessage = document.getElementById('error-signup');

    fetch("/api/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    }).then(r => r.json())
        .then(data => {
            let info = JSON.parse(JSON.stringify(data)).error;
            console.log(info)
            if (info == "VALID") {
                errorMessage.innerText = info;
                window.location.href = '/';
                return true;
            }
            errorMessage.innerText = info;
            return false;
        })
        .catch(error => console.error(error));
}

function loginUser() {
    const form = document.getElementById("login-form");
    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());
    const errorMessage = document.getElementById('error-login');

    let email = JSON.parse(JSON.stringify(data)).email;
    let password =  JSON.parse(JSON.stringify(data)).password;


    fetch("/api/token", {
        method: "POST",
        body: JSON.stringify({email, password}),
        headers: {
            "Content-Type": "application/json",
        },
        credentials:'include'
    }).then(r => r.json())
        .then(data => {
            const json = JSON.parse(JSON.stringify(data));
            document.cookie='token='+ json.token + "; max-age=259200;"
            window.location.href = '/';
        })
        .catch(error => {
            console.error(error);
            errorMessage.innerText = 'An error occurred!';
        });

}
