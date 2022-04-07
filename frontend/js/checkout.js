$(window).on('load', function() {
    let user = sessionStorage.getItem("op");
    console.log(user);
    if(user != null){
        //user is logged in
        var value;
        function getData1() {
            amount = UrlParm.parm("amount");
            value = amount;
        }
        getData1();
    

    }else{
            login();
    }
})