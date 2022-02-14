package com.amazon.service;

import com.amazon.entity.Item;
import com.amazon.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> saveAll(Collection<Item> items) {
        return itemRepository.saveAll(items);
    }
}
