$(window).on('load', function() {
    let user = sessionStorage.getItem("op");
    console.log(user);
    if(user != null){
        //user is logged in

    }else{
            login();
    }
})