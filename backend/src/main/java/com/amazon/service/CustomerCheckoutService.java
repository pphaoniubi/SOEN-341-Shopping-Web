package com.amazon.service;

import com.amazon.entity.Account;
import com.amazon.entity.OrderHistory;
import com.amazon.entity.ShoppingCart;
import com.amazon.entity.ShoppingItem;
import com.amazon.repository.OrderHistoryRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CustomerCheckoutService {

    private final OrderHistoryRepository orderHistoryRepository;
    private final CustomerShoppingService customerShoppingService;

    public CustomerCheckoutService(OrderHistoryRepository orderHistoryRepository,
                                   CustomerShoppingService customerShoppingService) {
        this.orderHistoryRepository = orderHistoryRepository;
        this.customerShoppingService = customerShoppingService;
    }

    public OrderHistory saveOrderHistory(OrderHistory orderHistory) {
        return orderHistoryRepository.save(orderHistory);
    }

    public OrderHistory getOrderHistoryByShoppingCartId(Account account, int shoppingCartId) {
        ShoppingCart shoppingCart = customerShoppingService.findCustomerShoppingCart(account.getId(), shoppingCartId);
        List<ShoppingItem> shoppingItems = customerShoppingService.findAllShoppingItemsByShoppingCartId(shoppingCart.getId());
        if (CollectionUtils.isEmpty(shoppingItems)) {
            throw new IllegalArgumentException("There's no items in the shopping cart.");
        }
        Integer orderHistoryId = shoppingItems.stream().filter(shoppingItem -> Objects.nonNull(shoppingItem.getOrderHistoryId()))
                .map(ShoppingItem::getOrderHistoryId).findAny().orElse(null);
        return findOrderHistoryByAccountAndId(account, orderHistoryId);
    }

    public OrderHistory getOrderHistoryByOrderHistoryId(Account account, int orderHistoryId) {
        return findOrderHistoryByAccountAndId(account, orderHistoryId);
    }

    public OrderHistory findOrderHistoryByAccountAndId(Account account, Integer orderHistoryId) {
        if (Objects.nonNull(orderHistoryId)) {
            return orderHistoryRepository.findByAccountAndId(account, orderHistoryId);
        }
        throw new IllegalArgumentException("There's no checkout record for the customer.");
    }
}
