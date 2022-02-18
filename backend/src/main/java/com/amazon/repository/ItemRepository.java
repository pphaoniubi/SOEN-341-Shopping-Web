package com.amazon.repository;

import com.amazon.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findAllByIdIn(Collection<Integer> itemIds);
}
