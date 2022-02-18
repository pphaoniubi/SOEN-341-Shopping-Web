package com.amazon.service;

import com.amazon.entity.ShoppingCart;
import com.amazon.entity.ShoppingItem;
import com.amazon.repository.CustomerShoppingItemRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CustomerShoppingItemService {

    private final CustomerShoppingItemRepository customerShoppingItemRepository;

    public CustomerShoppingItemService(CustomerShoppingItemRepository customerShoppingItemRepository) {
        this.customerShoppingItemRepository = customerShoppingItemRepository;
    }

    public List<ShoppingItem> saveShoppingItems(Collection<ShoppingItem> shoppingItems) {
        return customerShoppingItemRepository.saveAll(shoppingItems);
    }

    public List<ShoppingItem> findAllByShoppingCart(ShoppingCart shoppingCart) {
        return customerShoppingItemRepository.findAllByShoppingCart(shoppingCart);
    }
}
