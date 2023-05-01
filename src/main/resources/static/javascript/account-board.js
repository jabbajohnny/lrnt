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

}