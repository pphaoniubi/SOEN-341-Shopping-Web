var $userBtn = $('#user-image');
var $logBtn = $('#log-btn');


$(window).on('load', function() {
    let user = sessionStorage.getItem("op");
    console.log(user);
    if(user != null){
        //user is logged in
        $(".account-info").html(`Welcome! ${JSON.parse(user).email}`);
    }else{

    }
})
function logout() {
    sessionStorage.clear;
    sessionStorage.setItem("toLogin", "1")

    window.location.href = "login.html";
}