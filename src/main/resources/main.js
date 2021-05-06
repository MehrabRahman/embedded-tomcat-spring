fetch("hello")
    .then(resp => resp.text())
    .then(data => document.getElementById("greeting").innerHTML = data)
