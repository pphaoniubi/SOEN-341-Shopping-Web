$(function() {
    let ac = $.cookie("ac");
    if (ac != undefined && ac.length > 0) {
        let account = JSON.parse(ac);
        $("#email").val(account.email);
        $("#password").val(account.password);
        if (sessionStorage.getItem("toLogin") != "1") {
            login();
        }
    } else {
        window.location.href = "login.html";
    }
    $("#loginBtn").click(function() {
        login();
    });
});

window.document.onkeyup = function(event) {
    if (event.keyCode == 13) {
        login();
    }
}

/*function Account(email, password, rememberme) {
    this.email = email;
    this.password = password;
    this.rememberme = rememberme;
}*/



function logout() {
    sessionStorage.clear();
    sessionStorage.setItem("toLogin", "1");
    window.location.replace("login.html");
}