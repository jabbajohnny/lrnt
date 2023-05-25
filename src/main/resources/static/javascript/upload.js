document.getElementById("upload-form").addEventListener("submit", function (event) {
    event.preventDefault();
    upload();
})

function upload() {
    const form = document.getElementById("upload-form");
    const fileInput = document.getElementById("upload-file");
    const formData = new FormData(form);
    const errorInfo = document.getElementById("error-info");

    console.log(formData.entries().next());

    fetch("/api/upload", {
        method: "POST",
        body: formData
    }).then(r => r.json())
        .then(data => {
            if (!data.ok) {
                errorInfo.innerText = JSON.parse(JSON.stringify(data)).error;
                return false;
            }
            window.location.href = "/";
            return true;
        })
        .catch(error => {
            console.error(error)
        });
}