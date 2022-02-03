
var xhttp = new XMLHttpRequest();
xhttp.open("GET", "", true);
xhttp.send();

var messages = ["Hello!", "Bye!", "Good morning!", "Good day!", "How are you doing?",
"Good night!", "How am I?", "Have a nice day!", "Good luck!", "Lunchtime!"]

xhttp.onreadystatechange = function () {
    if (xhttp.readyState == 4 && xhttp.status == 200) {
    setInterval(() => {
        var message = messages[Math.floor(Math.random() * messages.length)];
        console.log(message);
        var msg = document.getElementById("msg");
        msg.innerHTML = message;

     }, 3000);
    }
};
