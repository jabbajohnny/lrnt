document.getElementById("upload-form").addEventListener("submit", function (event) {
    console.log("ddd");
    event.preventDefault();
    upload();
})

function upload() {
    const form = document.getElementById("upload-form");
    const formData = new FormData(form);

    console.log("ddd");

    fetch("/api/upload", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: formData
    }).then(r => r.json())
        .then(data => {
            console.log(data);
        })
        .catch(error => console.error(error));
}