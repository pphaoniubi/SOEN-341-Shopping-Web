package com.amazon.controller;

import com.amazon.entity.Account;
import com.amazon.entity.Item;
import com.amazon.entity.ShoppingCart;
import com.amazon.entity.ShoppingItem;
import com.amazon.service.AccountService;
import com.amazon.service.CustomerShoppingCartService;
import com.amazon.service.CustomerShoppingItemService;
import com.amazon.service.ItemService;
import com.amazon.util.Util;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer/shoppingCart")
public class CustomerShoppingCartController {

    private final ItemService itemService;
    private final AccountService accountService;
    private final CustomerShoppingCartService customerShoppingCartService;
    private final CustomerShoppingItemService customerShoppingItemService;

    public CustomerShoppingCartController(ItemService itemService, AccountService accountService,
                                          CustomerShoppingCartService customerShoppingCartService,
                                          CustomerShoppingItemService customerShoppingItemService) {
        this.itemService = itemService;
        this.accountService = accountService;
        this.customerShoppingCartService = customerShoppingCartService;
        this.customerShoppingItemService = customerShoppingItemService;
    }

    @PostMapping
    public void addItemsToCart(@RequestBody Map<Integer, Integer> itemIdQuantityMap,
                               HttpServletResponse response) throws IOException {

        long accountId = Util.getCurrentUser().getAccountId();
        Optional<Account> accountOptional = accountService.findByAccountId(accountId);
        if (!accountOptional.isPresent()) {
            response.sendError(HttpStatus.NOT_FOUND.value(), "The account doesn't exist in our system. Please login again.");
            return;
        }
        Set<Integer> itemIds = itemIdQuantityMap.keySet();
        List<Item> items = itemService.findAllByItemIds(itemIds);
        if (CollectionUtils.isEmpty(items)) {
            response.sendError(HttpStatus.NOT_FOUND.value(), "These item ids don't exist in our system.");
            return;
        }
        Map<Integer, Item> itemMap = items.stream().collect(Collectors.toMap(item -> item.getId(), item -> item));
        double totalAmount = items.stream().mapToDouble(item -> itemIdQuantityMap.get(item.getId()) * item.getPrice()).sum();
        Account account = accountOptional.get();
        ShoppingCart shoppingCart = customerShoppingCartService.findShoppingCartByAccountId(accountId);
        if (Objects.isNull(shoppingCart)) {
            ShoppingCart newShoppingCart = new ShoppingCart(account, totalAmount);
            customerShoppingCartService.saveShoppingCart(newShoppingCart);
            List<ShoppingItem> shoppingItems = itemIdQuantityMap.entrySet().stream()
                    .filter(entry -> Objects.nonNull(itemMap.get(entry.getKey())))
                    .map(entry -> new ShoppingItem(itemMap.get(entry.getKey()), entry.getValue(),
                    itemMap.get(entry.getKey()).getPrice(), newShoppingCart)).collect(Collectors.toList());
            customerShoppingItemService.saveShoppingItems(shoppingItems);
        } else {
            shoppingCart.setTotalAmount(shoppingCart.getTotalAmount() + totalAmount);
            customerShoppingCartService.saveShoppingCart(shoppingCart);
            List<ShoppingItem> oldShoppingItems = customerShoppingItemService.findAllByShoppingCart(shoppingCart);
            for (ShoppingItem shoppingItem : oldShoppingItems) {
                if (items.contains(shoppingItem.getItem())) {
                    shoppingItem.setQuantity(shoppingItem.getQuantity() + itemIdQuantityMap.get(shoppingItem.getItem().getId()));
                }
            }
            customerShoppingItemService.saveShoppingItems(oldShoppingItems);
        }
    }
}
