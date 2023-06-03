const asset = document.querySelector("[asset-template]");
const assetContainer = document.querySelector("[asset-container]");
let assets = [];


// TODO: Make separate box for every asset - put them in nice layout
window.onload = function () {
    fetch("/api/assets")
        .then(r => r.json())
        .then(data => {
            assets = data.map(assetObject => {
                const assetCard = asset.content.cloneNode(true).querySelector(".assets");
                const title = assetCard.querySelector("[title]");
                const description = assetCard.querySelector("[description]")
                const author = assetCard.querySelector("[author]")
                const uploadDate = assetCard.querySelector("[upload-date]")

                title.textContent = assetObject.title;
                description.textContent = assetObject.description;
                author.textContent = assetObject.author;
                uploadDate.textContent = assetObject.upload_date;

                assetContainer.append(assetCard);
            })
        })
}