package com.amazon.service;

import com.amazon.entity.Item;
import com.amazon.repository.CustomerItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerItemService {

    private final CustomerItemRepository customerItemRepository;

    public CustomerItemService(CustomerItemRepository customerItemRepository) {
        this.customerItemRepository = customerItemRepository;
    }

    public Page<Item> findAllForSaleItems(Pageable pageable) {
         return customerItemRepository.findAllByForSaleTrue(pageable);
    }

    public Item findOneForSaleItemById(int id) {
        return customerItemRepository.findOneByIdAndForSaleTrue(id);
    }

    public Page<Item> findAllByBrand(String brand, Pageable pageable){

        return customerItemRepository.findAllByBrand(brand, pageable);
    }

    public Page<Item> findAllByName(String name, Pageable pageable){
        return customerItemRepository.findAllByName(name, pageable);
    }

    public Item save(Item item) {
        return customerItemRepository.save(item);
    }

    public List<Item> saveAll(Collection<Item> items) {
        return customerItemRepository.saveAll(items);
    }
}
