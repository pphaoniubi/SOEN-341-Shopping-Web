package com.amazon.dto;

import com.amazon.entity.ShoppingCart;
import com.amazon.entity.ShoppingItem;

import java.util.List;

public class CustomerShoppingCartDto {

    private ShoppingCart shoppingCart;
    private List<ShoppingItem> shoppingItems;

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public List<ShoppingItem> getShoppingItems() {
        return shoppingItems;
    }

    public void setShoppingItems(List<ShoppingItem> shoppingItems) {
        this.shoppingItems = shoppingItems;
    }
}
