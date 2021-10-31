function getAndLoadResponse(url,id){
    const xhttp = new XMLHttpRequest();
    // To load and display data
    xhttp.onload = function() {
        var temp = document.getElementById(id);
        temp.innerHTML = this.responseText;
    }
    xhttp.open("GET", url);
    xhttp.send();
}