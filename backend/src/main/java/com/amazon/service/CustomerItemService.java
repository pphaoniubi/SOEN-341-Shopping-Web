package com.amazon.service;

import com.amazon.entity.Item;
import com.amazon.repository.CustomerItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

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

    public Page<Item> findAllForSaleItemsBySimilarName(String name, Pageable pageable){
        return customerItemRepository.findAllByNameContainingIgnoreCaseAndForSaleTrue(name, pageable);
    }



    public Item save(Item item) {
        return customerItemRepository.save(item);
    }

    public List<Item> saveAll(Collection<Item> items) {
        return customerItemRepository.saveAll(items);
    }
}
