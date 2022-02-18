package com.amazon.service;

import com.amazon.entity.ShoppingCart;
import com.amazon.repository.CustomerShoppingCartRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerShoppingCartService {
    private final CustomerShoppingCartRepository customerShoppingCartRepository;

    public CustomerShoppingCartService(CustomerShoppingCartRepository customerShoppingCartRepository) {
        this.customerShoppingCartRepository = customerShoppingCartRepository;
    }

    public ShoppingCart findShoppingCartByAccountId(int accountId) {
        return customerShoppingCartRepository.findByAccount_Id(accountId);
    }

    public ShoppingCart saveShoppingCart(ShoppingCart shoppingCart) {
        return customerShoppingCartRepository.save(shoppingCart);
    }
}
