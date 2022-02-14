package com.amazon.controller;

import com.amazon.dto.ItemDetailDto;
import com.amazon.dto.ItemListDto;
import com.amazon.entity.Item;
import com.amazon.mapper.ItemDetailMapper;
import com.amazon.mapper.ItemListMapper;
import com.amazon.service.CustomerItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/customer/item")
public class CustomerItemController {

    private final CustomerItemService customerItemService;

    public CustomerItemController(CustomerItemService itemService) {
        this.customerItemService = itemService;
    }

    @GetMapping
    public Page<ItemListDto> findAllForSaleItems(Pageable pageable) {
        Page<Item> itemPage = customerItemService.findAllForSaleItems(pageable);
        List<ItemListDto> itemListDtos = ItemListMapper.INSTANCE.map(itemPage.getContent());
        return new PageImpl<>(itemListDtos, pageable, itemPage.getTotalElements());
    }

    @GetMapping("/{id}")
    public ItemDetailDto findForSaleItemById(@PathVariable("id") int id,
                                             HttpServletResponse response) throws IOException {
        Item item = customerItemService.findOneForSaleItemById(id);
        if (Objects.nonNull(item)) {
            return ItemDetailMapper.INSTANCE.map(item);
        }
        response.sendError(HttpStatus.NOT_FOUND.value(), "There's no such item.");
        return null;
    }

    public List<Item> findAllByname(String name){
        return customerItemService.findAllByName(name);
    }
    public List<Item> findAllByBrand(String brand){
        return customerItemService.findAllByBrand(brand);
    }

    @PostMapping
    public Item saveItem() {
        return new Item();
    }


}
