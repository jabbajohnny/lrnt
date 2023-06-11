const title = document.querySelector("[title]");
const description = document.querySelector("[description]");

const audioPlayer = document.getElementById("audio-player");
const audioSource = document.querySelector("[source]");

const assetId = window.location.href.slice(-11);

document.addEventListener("DOMContentLoaded", () => {


    fetch("/api/asset/" + assetId)
        .then(r => r.json())
        .then(data => {
            title.textContent = data[0].title;
            description.textContent = data[0].description;

            audioSource.src = "http://localhost:8080/api/asset/" + assetId + "/audio";

            audioPlayer.load();
            
        })

})

audioPlayer.onseeking = function () {
    console.log(audioPlayer.currentTime);
    audioSource.src = "http://localhost:8080/api/asset/" + assetId + "/audio?seek=" + audioPlayer.currentTime;
}