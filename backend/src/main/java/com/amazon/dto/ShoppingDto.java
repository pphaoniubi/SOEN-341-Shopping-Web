package com.amazon.dto;

import java.util.List;

public class ShoppingDto {

    private ShoppingCartDto shoppingCartDto;
    private List<ShoppingItemDto> shoppingItemDtos;

    public ShoppingCartDto getShoppingCartDto() {
        return shoppingCartDto;
    }

    public void setShoppingCartDto(ShoppingCartDto shoppingCartDto) {
        this.shoppingCartDto = shoppingCartDto;
    }

    public List<ShoppingItemDto> getShoppingItemDtos() {
        return shoppingItemDtos;
    }

    public void setShoppingItemDtos(List<ShoppingItemDto> shoppingItemDtos) {
        this.shoppingItemDtos = shoppingItemDtos;
    }
}
