const title = document.querySelector("[title]");
const description = document.querySelector("[description]");
const uploadDate = document.querySelector("[upload-date]");
const author = document.querySelector("[author]");

const audioPlayer = document.getElementById("audio-player");
const audioSource = document.querySelector("[source]");
const seekButton = document.getElementById("seek-button");
const assetId = window.location.href.slice(-11);

document.addEventListener("DOMContentLoaded", () => {


    fetch("/api/asset/" + assetId)
        .then(r => r.json())
        .then(data => {
            title.textContent = data[0].title;
            description.textContent = data[0].description;
            uploadDate.textContent = data[0].upload_date;
            author.textContent = data[0].author;

            audioSource.src = "http://localhost:8080/api/asset/" + assetId + "/audio";

            audioPlayer.load();

        })

})

audioPlayer.onseeking = function () {
    console.log(audioPlayer.currentTime);
    audioSource.src = "http://localhost:8080/api/asset/" + assetId + "/audio?seek=" + audioPlayer.currentTime;
}

seekButton.onclick = function () {
    audioSource.src = "http://localhost:8080/api/asset/" + assetId + "/audio?seek=" + 10;
    audioPlayer.load();
    audioPlayer.start();
}