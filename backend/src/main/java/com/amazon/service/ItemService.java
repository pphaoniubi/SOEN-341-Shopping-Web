package com.amazon.service;

import com.amazon.entity.Item;
import com.amazon.repository.ItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> findAllByItemIds(Collection<Integer> itemIds) {
        List<Item> items = itemRepository.findAllByIdIn(itemIds);
        if (CollectionUtils.isEmpty(items)) {
            throw new IllegalArgumentException("These item ids don't exist in our system.");
        }
        return items;
    }
}
