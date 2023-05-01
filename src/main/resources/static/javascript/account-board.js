window.onload = function (){
    let username = document.getElementById("username");


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
        })
        .catch(error => {
            console.error(error);
        });
}