var $userBtn = $('#user-image');
var $logBtn = $('#log-btn');


$(window).on('load', function() {
    let user = sessionStorage.getItem("op");
    console.log(user);
    if(user != null){
        //user is logged in
        $(".account-info").html(`Welcome! ${JSON.parse(user).email}`);
        $("#log-btn").on('click', function() {
            logout();
        })
    }else{
        $(".account-info").html(`login in place order`);
        $("#log-btn").html('log in')
        $("#log-btn").on('click', function() {
            login();
        })
    }
})
function logout() {

    sessionStorage.clear;
    sessionStorage.setItem("toLogin", "1")

    window.location.href = "login.html";
}
function login(){
    window.location.href = "login.html";
}