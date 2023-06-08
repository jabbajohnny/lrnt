const title = document.querySelector("[title]");
const description = document.querySelector("[description]");

document.addEventListener("DOMContentLoaded", () => {

    const assetId = window.location.href.slice(-11);

    fetch("/api/asset/" + assetId)
        .then(r => r.json())
        .then(data => {
            console.log(data);
            console.log(data.title)
            title.textContent = data[0].title;
            description.textContent = data[0].description;
        })
})