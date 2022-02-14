package com.amazon.repository;

import com.amazon.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerItemRepository extends JpaRepository<Item, Integer> {
    Page<Item> findAllByForSaleTrue(Pageable pageable);
    Item findOneByIdAndForSaleTrue(int id);
    Page<Item> findAllByName(String name, Pageable pageable);
    Page<Item> findAllByBrand(String brand, Pageable pageable);
}
