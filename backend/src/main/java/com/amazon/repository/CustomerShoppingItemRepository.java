package com.amazon.repository;

import com.amazon.entity.ShoppingCart;
import com.amazon.entity.ShoppingItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerShoppingItemRepository extends JpaRepository<ShoppingItem, Integer> {
    List<ShoppingItem> findAllByShoppingCart(ShoppingCart shoppingCart);
}
