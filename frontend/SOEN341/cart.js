console.log('Hello world')
var removecarteButtoms=document.getElementsByClassName("btn-rm")
console.log(removecarteButtoms)

for(var i=0;i<removecarteButtoms.length;i++)
{
    var button=removecarteButtoms[i]
    button.addEventListener('click',function(event){
        var removebuttoncliked=event.target
        //removebuttoncliked.parentElement.parentElement.parentElement.remove()
        removebuttoncliked.closest('tr').remove()   


    });
}/*
function updataCarteTotal(){
    var cartPage=document.getElementsByClassName("small-container cart-page")[0]
    var cartItems = cartPage.getElementsByClassName('"cart-items')
    console.log(cartItems)
    
    var total = 0
    for (var i = 0; i < cartItems.length; i++) {
        var cartitems = cartItems[i]
        var priceElement = cartitems.getElementsByClassName('cart-price')[0]
        var quantityElement = cartitems.getElementsByClassName('cart-quantity-input')[0]
        
        var price = parseFloat(priceElement.innerText.replace('$', ''))
        var quantity = quantityElement.value
        total = total + (price * quantity)
    }
    total = Math.round(total * 100) / 100
    document.getElementsByClassName('cart-total-price')[0].innerText = '$' + total
    

}
*/
