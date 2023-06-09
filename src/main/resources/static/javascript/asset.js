const title = document.querySelector("[title]");
const description = document.querySelector("[description]");



const assetId = window.location.href.slice(-11);


document.addEventListener("DOMContentLoaded", () => {


    fetch("/api/asset/" + assetId)
        .then(r => r.json())
        .then(data => {
            title.textContent = data[0].title;
            description.textContent = data[0].description;


            const audioPlayer = document.getElementById("audio-player");
            const audioSource = document.querySelector("[source]");

            audioSource.src = "http://localhost:8080/api/asset/" + assetId + "/audio";

            audioPlayer.load();
        })
})