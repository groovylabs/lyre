var successful = function() {
    document.getElementById("error").style.display = "none";
}

var error = function() {
    document.getElementById("error").innerHTML = "Whoops! No one Lyre UI Application detected. <a href='https://github.com/groovylabs/lyre-ui'>See</a> and start to use right now =]";
}

var response = function(success) {
    if (success)
        successful();
    else
        error();
}

function httpGetAsync(theUrl, callback) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
            response(true);
        else
            response(false);
    }
    xmlHttp.open("GET", theUrl, true); // true for asynchronous
    xmlHttp.send(null);
}

httpGetAsync("http://localhost:4200/", response);
