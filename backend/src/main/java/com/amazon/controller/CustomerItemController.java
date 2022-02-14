package com.amazon.controller;

import com.amazon.entity.Item;
import com.amazon.service.CustomerItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer/item")
public class CustomerItemController {

    private final CustomerItemService customerItemService;

    public CustomerItemController(CustomerItemService itemService) {
        this.customerItemService = itemService;
    }

    @GetMapping
    public Page<Item> findAllItems(Pageable pageable) {
        return null;
    }

    public List<Item> findAllByname(String name){
        return customerItemService.findAllByName(name);
    }
    public List<Item> findAllByBrand(String brand){
        return customerItemService.findAllByBrand(brand);
    }
        //Page<ItemDto> 再放pageable
        //返回给前端
    @PostMapping
    public Item saveItem() {
        return new Item();
    }


}
