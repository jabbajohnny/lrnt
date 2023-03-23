document.getElementById("signup").addEventListener("submit", function (event) {
    event.preventDefault();
    registerUser();
});

function registerUser() {
    const form = document.getElementById("signup");
    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());
    const errorMessage = document.getElementById('error');

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

