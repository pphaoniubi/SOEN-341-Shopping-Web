package com.amazon.repository;

import com.amazon.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerItemRepository extends JpaRepository<Item, Integer> {

    Page<Item> findAllByForSaleTrue(Pageable pageable);
    Item findOneByIdAndForSaleTrue(int id);
    Page<Item> findAllByNameContainingIgnoreCaseAndForSaleTrue(String name, Pageable pageable);
    Page<Item> findAllByBrandIgnoreCaseAndForSaleTrue(String brand, Pageable pageable);

    @Query(value = "SELECT UPPER(brand) FROM item WHERE for_sale = true GROUP BY UPPER(brand)", nativeQuery = true)
    List<String> findAllForSaleBrands();
}
