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
            console.log(JSON.stringify(data))

            if (!data.toString().match("VALID")) {
                errorMessage.innerText = JSON.stringify(data);
                return false;
            }
        })
        .catch(error => console.error(error));
}

