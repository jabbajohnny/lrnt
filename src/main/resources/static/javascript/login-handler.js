document.getElementById("signup-form").addEventListener("submit", function (event) {
    event.preventDefault();
    registerUser();
});

document.getElementById("login-form").addEventListener("submit", function(event) {
    event.preventDefault()
    loginUser();
});



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
            "Content-Type": "application/json"
        },
        credentials:'include'
    }).then(r => r.json())
        .then(data => {
            console.log(JSON.parse(JSON.stringify(data)).token)
        })
        .catch(error => {
            console.error(error)
            errorMessage.innerText = 'An error occurred!';
        });

    fetch("/api/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    }).then(r => r.json())
        .then(data => {
            let info = JSON.parse(JSON.stringify(data)).result;
            console.log(info)
            if (info == "LOGGED_IN") {
                errorMessage.innerText = info;
                window.location.href = '/';
                return true;
            }
            errorMessage.innerText = info;
            return false;
        })
        .catch(error => {
            console.error(error)
            errorMessage.innerText = 'An error occurred!';
        });
}
