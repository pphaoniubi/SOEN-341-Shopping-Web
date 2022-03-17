
var removecarteButtoms=document.getElementsByClassName("btn-rm")
console.log(removecarteButtoms)

$(window).on('load', function() {
    let user = sessionStorage.getItem("op");
    console.log(user);
    if(user != null){
        //user is logged in

    }else{
            login();
    }
})

for(var i=0;i<removecarteButtoms.length;i++)
{
    var button=removecarteButtoms[i]
    button.addEventListener('click',function(event){
        var removebuttoncliked=event.target
        removebuttoncliked.parentElement.parentElement.parentElement.parentElement.remove()
        //removebuttoncliked.closest('tr').remove()  
        updateCartTotal() 
    });
}

function updateCartTotal(){
    var cartItems = document.getElementsByClassName('small-container cart-page')[0]
    var cartRows = cartItems.getElementsByClassName('item-info')
    console.log(cartItems)
    
    var total = 0
    for (var i = 0; i < cartRows.length; i++) {
        var cartRow = cartRows[i]
        var quantity = cartItems.getElementsByClassName('cart-quantity-input')[0]
        var priceElement = cartRow.getElementsByClassName('cart-price')[0]
        //var quantityElement = cartQuantity.getElementsByClassName('cart-quantity-input')[0] //non-existent
        quantity = quantity.value
        console.log(quantity)
        var price = parseFloat(priceElement.innerText.replace('$', ''))
        console.log(price)
        total = total + (price * quantity)
    }
    total = Math.round(total * 100) / 100
    console.log(total)
    document.getElementsByClassName('total-price')[0].innerText = '$' + total

}

