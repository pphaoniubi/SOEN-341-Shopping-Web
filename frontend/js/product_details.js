$(function() {

    var $products = $("#products");
    var $product = $("#product");
    var url;
    function getData1() {
        Id = UrlParm.parm("id");
        url = BACKEND_DOMAIN + '/item/' + Id;
    }
    getData1();

    $.ajax({
        type: 'GET',
        url: url,
        dataType: 'json',

        success: function (jqXHR) {

            var parsedJson = jqXHR;
            $("#test").html("<img src='" + jqXHR.thumbnail + "'/>");
            $("#current").html("<h4>" + jqXHR.price + "$" + "</h4>");
            $("#name").html("<h1>" + jqXHR.name + "</h1>");
            $("#brand").html("<h1>" + jqXHR.brand + "</h1>");
        }
        ,
        error: function (jqXHR) {
            alert(jqXHR.statusText);
        }
    });
    $('.btn').on( 'click', function() {
        let ac = sessionStorage.getItem("ac");
        let itemId = UrlParm.parm("id");
        let quantity = $('#quantity').val();
        
        var product = [{
            itemId: itemId,
            quantity: quantity,
        }];
        console.log(product);

        if (ac != undefined && ac.length > 0) {
            $.ajax(BACKEND_DOMAIN + "/customer/shoppingCart", {
                method: 'POST',
                data: JSON.stringify(product),
                crossDomain: true,
                xhrFields: {withCredentials: true},
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    alert("Item is added into shopping cart! " + JSON.stringify(data));
                    window.location.replace("index.html");
                },
                error: function (errMsg) {
                    alert('error');
                }
            });
        } else {
            window.location.href = "login.html";
        };
    });

    
})


