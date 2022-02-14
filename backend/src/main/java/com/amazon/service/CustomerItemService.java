package com.amazon.service;

import com.amazon.entity.Item;
import com.amazon.repository.CustomerItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CustomerItemService {

    private final CustomerItemRepository customerItemRepository;

    public CustomerItemService(CustomerItemRepository customerItemRepository) {
        this.customerItemRepository = customerItemRepository;
    }
    public List<Item> findAllByBrand(String brand){
        return customerItemRepository.findAllByBrand(brand);
    }

    public List<Item> findAllByName(String name){
        return customerItemRepository.findAllByName(name);
    }
    public List<Item> findAllItems() {
        return customerItemRepository.findAll();
    }

    public Item save(Item item) {
        return customerItemRepository.save(item);
    }

    public List<Item> saveAll(Collection<Item> items) {
        return customerItemRepository.saveAll(items);
    }
}
