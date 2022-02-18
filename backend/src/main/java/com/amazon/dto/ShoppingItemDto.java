package com.amazon.dto;

import com.amazon.entity.Item;
import com.amazon.entity.ShoppingCart;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class ShoppingItemDto {

    private int id;
    private Item item;
    private int quantity;
    private ShoppingCart shoppingCart;
    private int orderId;
}
