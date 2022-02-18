package com.amazon.repository;

import com.amazon.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
    ShoppingCart findByAccount_Id(int accountId);
}
