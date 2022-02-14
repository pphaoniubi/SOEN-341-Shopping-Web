package com.amazon.repository;

import com.amazon.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerItemRepository extends JpaRepository<Item, Long> {

    Page findAllByName(String name);
    Page findAllByBrand(String brand);

}
