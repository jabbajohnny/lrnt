document.getElementById("upload-form").addEventListener("submit", function (event) {
    console.log("ddd");
    event.preventDefault();
    upload();
})

function upload() {
    const form = document.getElementById("upload-form");
    const fileInput = document.getElementById("upload-file");
    const formData = new FormData(form);


    console.log(formData.entries().next());

    fetch("/api/upload", {
        method: "POST",
        body: formData
    }).then(r => r.json())
        .then(data => {
            console.log(data);
        })
        .catch(error => console.error(error));
}