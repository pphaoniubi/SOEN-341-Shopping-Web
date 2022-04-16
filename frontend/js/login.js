$(function() {
    let ac = $.cookie('ac');
    if(ac != undefined && ac.length > 0){
        let operator = JSON.parse(ac);
        $('#email').val(operator.email);
        $('#password').val(operator.password);
        if(sessionStorage.getItem("toLogin") != "1"){
            login();
        }
        
    }
    $('#login-btn').on('click', function() {
        login();
        
    })
});

//using enter to send login too
window.document.onkeyup = function(event) {
    if(event.onkeyup == 13){
        login();
    }
}



function login() {
    let email = $('#email').val();
    let password = $('#password').val();
    let remember = $('#remember-me').is(':checked');

    if(email.length == 0){
        alert("email cannot be empty");
        $('#email').focus();
    }
    else if(password.length == 0){
        alert('password cannot be empty');
        $('password').focus();
    }else{
        let user = {
            email: email,
            password: password,
            remember: remember,
        };
        if(remember){
            rememberuser(user);
        }
        
        sessionStorage.setItem("ac", JSON.stringify(user));
        $.ajax({
            type: "post",
            //url: BACKEND_DOMAIN + '/account/login',
           url: BACKEND_DOMAIN +"/account/login",
            // The key needs to match your method's input parameter (case-sensitive).
            data: JSON.stringify(user),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            headers: {'api-key': 'myKey'},
//            xhrFields: {
  //              withCredentials: true
    //        },
            
            success: function (data) {
                console.log(data.id);
                window.location.href = "index.html";
            },
            error: function (errMsg) {
                alert('error');
            }
        });
    }
}

function rememberuser(user) {
    $.cookie('ac', JSON.stringify(user), {
        expires: 7
    });
}