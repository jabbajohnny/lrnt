const asset = document.querySelector("[assets-template]");
const assetContainer = document.querySelector("[asset-container]");
let assets = [];

window.onload = function (){
    let username = document.getElementById("username");
    let avatar = document.getElementById("avatar");

    let jsonUsername;
    let avatarSrc;

    fetch("/api/getUserData", {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        },
        credentials:'include'
    }).then(r => r.json())
        .then(data => {
            const json = JSON.parse(JSON.stringify(data));
            username.innerText = json.username;
            avatarSrc = "https://api.dicebear.com/6.x/pixel-art-neutral/svg?seed=" + json.username;
            avatar.src = avatarSrc;
            console.log(avatarSrc);
        })
        .catch(error => {
            console.error(error);
        });

    fetch("/api/asset/allByUser")
        .then(r => r.json())
        .then(data => {
            assets = data.map(assetObject => {
                const assetCard = asset.content.cloneNode(true).querySelector(".assets");
                const title = assetCard.querySelector("[title]");
                const description = assetCard.querySelector("[description]")
                const author = assetCard.querySelector("[author]")
                const uploadDate = assetCard.querySelector("[upload-date]")
                const link = assetCard.querySelector("[link]");

                if (assetObject.description.length > 115) {
                    description.textContent = assetObject.description.substring(0, 115) + "...";
                } else {
                    description.textContent = assetObject.description;
                }
                title.textContent = assetObject.title;
                author.textContent = assetObject.author;
                uploadDate.textContent = assetObject.upload_date;

                link.href = "/" + assetObject.id;

                assetContainer.append(assetCard);
            })
        })

}

document.getElementById("logout-button").addEventListener("click", function (event) {
    document.cookie = "token=;path=/;expires=" + new Date(0).toUTCString();
    window.location.href = "/"
})