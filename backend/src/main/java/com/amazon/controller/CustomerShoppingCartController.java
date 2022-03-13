package com.amazon.controller;

import com.amazon.dto.ShoppingCartChangeDto;
import com.amazon.dto.ShoppingDto;
import com.amazon.entity.Account;
import com.amazon.entity.Item;
import com.amazon.entity.ShoppingCart;
import com.amazon.entity.ShoppingItem;
import com.amazon.service.AccountService;
import com.amazon.service.CustomerShoppingService;
import com.amazon.service.ItemService;
import com.amazon.util.Util;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer/shoppingCart")
public class CustomerShoppingCartController {

    private final ItemService itemService;
    private final AccountService accountService;
    private final CustomerShoppingService customerShoppingService;

    public CustomerShoppingCartController(ItemService itemService, AccountService accountService,
                                          CustomerShoppingService customerShoppingService) {
        this.itemService = itemService;
        this.accountService = accountService;
        this.customerShoppingService = customerShoppingService;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ShoppingDto queryShoppingCartInfo() {
        int accountId = Util.getCurrentUser().getAccountId();
        Account account = accountService.findById(accountId);
        return customerShoppingService.findShoppingInfo(account.getId());
    }

    @PostMapping
    @Transactional
    public ShoppingDto addItemsToShoppingCart(@RequestBody List<ShoppingCartChangeDto> shoppingCartChangeDtos) {
        int accountId = Util.getCurrentUser().getAccountId();
        Account account = accountService.findById(accountId);
        List<Integer> itemIds = shoppingCartChangeDtos.stream().map(ShoppingCartChangeDto::getItemId).collect(Collectors.toList());
        List<Item> items = itemService.findAllByItemIds(itemIds);
        Map<Integer, Integer> itemIdQuantityMap = shoppingCartChangeDtos.stream().collect(Collectors
                .toMap(dto -> dto.getItemId(), dto -> dto.getQuantity()));
        return customerShoppingService.addShoppingInfo(account, items, itemIdQuantityMap);
    }

    @PutMapping("/{shoppingCartId}")
    @Transactional
    public ShoppingDto updateItemsInShoppingCart(@PathVariable int shoppingCartId,
                                                 @RequestBody List<ShoppingCartChangeDto> shoppingCartChangeDtos) {
        int accountId = Util.getCurrentUser().getAccountId();
        Account account = accountService.findById(accountId);
        ShoppingCart shoppingCart = customerShoppingService.findCustomerShoppingCart(account.getId(), shoppingCartId);
        List<ShoppingItem> shoppingItems = customerShoppingService.findAllShoppingItemsByShoppingCartId(shoppingCart.getId());
        Map<Integer, Integer> itemIdQuantityMap = shoppingCartChangeDtos.stream().collect(Collectors
                .toMap(dto -> dto.getItemId(), dto -> dto.getQuantity()));
        return customerShoppingService.updateShoppingInfo(shoppingCart, shoppingItems, itemIdQuantityMap);
    }

    @DeleteMapping("/{shoppingCartId}/shoppingItem")
    @Transactional
    public ShoppingDto deleteItemsInShoppingCart(@PathVariable int shoppingCartId,
                                                 @RequestBody List<Integer> itemIds) {
        int accountId = Util.getCurrentUser().getAccountId();
        Account account = accountService.findById(accountId);
        ShoppingCart shoppingCart = customerShoppingService.findCustomerShoppingCart(account.getId(), shoppingCartId);
        List<ShoppingItem> shoppingItems = customerShoppingService.findAllShoppingItemsByShoppingCartId(shoppingCart.getId());
        List<ShoppingItem> deleteShoppingItems = shoppingItems.stream().filter(shoppingItem
                -> itemIds.contains(shoppingItem.getItem().getId())).collect(Collectors.toList());
        return customerShoppingService.removeShoppingItems(shoppingCart, deleteShoppingItems);
    }
}
