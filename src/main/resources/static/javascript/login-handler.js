document.getElementById("signup").addEventListener("submit", function (event) {
    event.preventDefault();
    sendData();
});

function sendData() {
    const form = document.getElementById("signup");
    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());

    console.log(JSON.stringify(data));

    fetch("/api/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    }).then(r => r.json)
        .then(data => console.log(data))
        .catch(error => console.error(error));
}

