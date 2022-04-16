package com.amazon.service;

import com.amazon.dto.ShoppingDto;
import com.amazon.entity.Account;
import com.amazon.entity.Item;
import com.amazon.entity.OrderHistory;
import com.amazon.entity.ShoppingCart;
import com.amazon.entity.ShoppingItem;
import com.amazon.mapper.ShoppingCartMapper;
import com.amazon.mapper.ShoppingItemMapper;
import com.amazon.repository.CustomerShoppingCartRepository;
import com.amazon.repository.CustomerShoppingItemRepository;
import com.amazon.repository.OrderHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerShoppingService {

    private final CustomerShoppingCartRepository customerShoppingCartRepository;
    private final CustomerShoppingItemRepository customerShoppingItemRepository;
    private final OrderHistoryRepository orderHistoryRepository;

    public CustomerShoppingService(CustomerShoppingCartRepository customerShoppingCartRepository,
                                   CustomerShoppingItemRepository customerShoppingItemRepository,
                                   OrderHistoryRepository orderHistoryRepository) {
        this.customerShoppingCartRepository = customerShoppingCartRepository;
        this.customerShoppingItemRepository = customerShoppingItemRepository;
        this.orderHistoryRepository = orderHistoryRepository;
    }

    public ShoppingCart findCustomerShoppingCart(int accountId, int shoppingCartId) {
        Optional<ShoppingCart> shoppingCartOptional = customerShoppingCartRepository.findById(shoppingCartId);
        ShoppingCart shoppingCart;
        if (shoppingCartOptional.isPresent()) {
            shoppingCart = shoppingCartOptional.get();
            if (shoppingCart.getAccount().getId().equals(accountId)) {
                return shoppingCart;
            }
        }
        throw new IllegalArgumentException("There's no such shopping cart.");
    }

    public ShoppingCart findShoppingCartByAccountId(int accountId) {
        return customerShoppingCartRepository.findByAccount_Id(accountId);
    }

    public void deleteShoppingCart(ShoppingCart shoppingCart) {
        customerShoppingCartRepository.delete(shoppingCart);
    }

    private ShoppingCart saveShoppingCart(ShoppingCart shoppingCart) {
        return customerShoppingCartRepository.save(shoppingCart);
    }

    public List<ShoppingItem> saveShoppingItems(Collection<ShoppingItem> shoppingItems) {
        return customerShoppingItemRepository.saveAll(shoppingItems);
    }

    public List<ShoppingItem> findAllShoppingItemsByShoppingCartId(int shoppingCartId) {
        return customerShoppingItemRepository.findAllByShoppingCart_Id(shoppingCartId);
    }

    public List<ShoppingItem> findAllShoppingItemsByOrderHistoryId(int orderHistoryId) {
        return customerShoppingItemRepository.findAllByOrderHistoryId(orderHistoryId);
    }

    public ShoppingDto removeShoppingItems(ShoppingCart shoppingCart, Collection<ShoppingItem> shoppingItems) {
        double deductAmount = shoppingItems.stream().mapToDouble(shoppingItem -> shoppingItem.getQuantity() * shoppingItem.getPrice()).sum();
        customerShoppingItemRepository.deleteAll(shoppingItems);
        shoppingCart.setTotalAmount(shoppingCart.getTotalAmount() - deductAmount);
        customerShoppingCartRepository.save(shoppingCart);
        return findShoppingInfo(shoppingCart);
    }

    public ShoppingDto findShoppingInfo(ShoppingCart shoppingCart) {
        List<ShoppingItem> shoppingItems = findAllShoppingItemsByShoppingCartId(shoppingCart.getId());
        ShoppingDto dto = new ShoppingDto();
        dto.setShoppingCartDto(ShoppingCartMapper.INSTANCE.map(shoppingCart));
        dto.setShoppingItemDtos(ShoppingItemMapper.INSTANCE.map(shoppingItems));
        return dto;
    }

    public ShoppingDto findShoppingInfo(int accountId) {
        ShoppingCart shoppingCart = findShoppingCartByAccountId(accountId);
        if (Objects.isNull(shoppingCart)) {
            return null;
        }
        return findShoppingInfo(shoppingCart);
    }

    public ShoppingDto addShoppingInfo(Account account, List<Item> items, Map<Integer, Integer> itemIdQuantityMap) {
        Map<Integer, Item> itemMap = items.stream().collect(Collectors.toMap(item -> item.getId(), item -> item));
        double totalAmount = items.stream().mapToDouble(item -> itemIdQuantityMap.get(item.getId()) * item.getPrice()).sum();
        ShoppingCart shoppingCart = findShoppingCartByAccountId(account.getId());
        List<ShoppingItem> shoppingItems;
        if (Objects.isNull(shoppingCart)) {
            shoppingCart = new ShoppingCart(account, totalAmount);
            saveShoppingCart(shoppingCart);
            final ShoppingCart newShoppingCart = shoppingCart;
            shoppingItems = itemIdQuantityMap.entrySet().stream()
                    .filter(entry -> Objects.nonNull(itemMap.get(entry.getKey())))
                    .map(entry -> new ShoppingItem(itemMap.get(entry.getKey()), entry.getValue(),
                            itemMap.get(entry.getKey()).getPrice(), newShoppingCart, null)).collect(Collectors.toList());
            customerShoppingItemRepository.saveAll(shoppingItems);
        } else {
            shoppingItems = findAllShoppingItemsByShoppingCartId(shoppingCart.getId());
            Integer orderHistoryId = shoppingItems.stream().filter(shoppingItem
                    -> Objects.nonNull(shoppingItem.getOrderHistoryId()))
                    .map(ShoppingItem::getOrderHistoryId).findAny().orElse(null);
            if (Objects.nonNull(orderHistoryId)) {
                OrderHistory orderHistory = orderHistoryRepository.findById(orderHistoryId).get();
                orderHistory.setTotalAmount(shoppingCart.getTotalAmount() + totalAmount);
                orderHistoryRepository.save(orderHistory);
            }
            shoppingCart.setTotalAmount(shoppingCart.getTotalAmount() + totalAmount);
            saveShoppingCart(shoppingCart);
            Map<Integer, ShoppingItem> shoppingItemMap = shoppingItems.stream()
                    .collect(Collectors.toMap(shoppingItem -> shoppingItem.getItem().getId(), shoppingItem -> shoppingItem));
            List<Integer> shoppingItemIds = shoppingItems.stream().map(shoppingItem -> shoppingItem.getItem().getId()).collect(Collectors.toList());
            for (Item item : items) {
                if (shoppingItemIds.contains(item.getId())) {
                    ShoppingItem shoppingItem = shoppingItemMap.get(item.getId());
                    shoppingItem.setQuantity(shoppingItem.getQuantity() + itemIdQuantityMap.get(shoppingItem.getItem().getId()));
                } else {
                    ShoppingItem shoppingItem = new ShoppingItem(item, itemIdQuantityMap.get(item.getId()),
                            item.getPrice(), shoppingCart, orderHistoryId);
                    shoppingItems.add(shoppingItem);
                }
            }
        }
        saveShoppingItems(shoppingItems);
        return findShoppingInfo(shoppingCart);
    }

    public ShoppingDto updateShoppingInfo(ShoppingCart shoppingCart, List<ShoppingItem> shoppingItems, Map<Integer, Integer> itemIdQuantityMap) {
        Map<Integer, ShoppingItem> shoppingItemMap = shoppingItems.stream().collect(Collectors
                .toMap(shoppingItem -> shoppingItem.getItem().getId(), shoppingItem -> shoppingItem));
        for (Map.Entry<Integer, Integer> entry : itemIdQuantityMap.entrySet()) {
            if (shoppingItemMap.containsKey(entry.getKey()) && entry.getValue() > 0) {
                shoppingItemMap.get(entry.getKey()).setQuantity(entry.getValue());
            }
        }
        saveShoppingItems(shoppingItems);
        double totalAmount = shoppingItems.stream().mapToDouble(shoppingItem -> shoppingItem.getQuantity() * shoppingItem.getPrice()).sum();
        shoppingCart.setTotalAmount(totalAmount);
        saveShoppingCart(shoppingCart);
        return findShoppingInfo(shoppingCart);
    }
}
