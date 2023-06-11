const asset = document.querySelector("[asset-template]");
const assetContainer = document.querySelector("[asset-container]");
let assets = [];

window.onload = function () {
    fetch("/api/asset/all")
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