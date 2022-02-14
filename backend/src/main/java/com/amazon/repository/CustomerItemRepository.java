package com.amazon.repository;

import com.amazon.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerItemRepository extends JpaRepository<Item, Integer> {

    Page<Item> findAllByForSaleTrue(Pageable pageable);
    Item findOneByIdAndForSaleTrue(int id);
    List<Item> findAllByName(String name);
    List<Item> findAllByBrand(String brand);

}
