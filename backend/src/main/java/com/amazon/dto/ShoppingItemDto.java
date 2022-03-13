package com.amazon.dto;

public class ShoppingItemDto {

    private int id;
    private ItemDetailDto itemDetailDto;
    private int quantity;
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ItemDetailDto getItemDetailDto() {
        return itemDetailDto;
    }

    public void setItemDetailDto(ItemDetailDto itemDetailDto) {
        this.itemDetailDto = itemDetailDto;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
