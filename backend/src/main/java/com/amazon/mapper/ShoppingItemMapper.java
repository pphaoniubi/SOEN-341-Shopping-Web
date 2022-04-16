package com.amazon.mapper;

import com.amazon.dto.ShoppingItemDto;
import com.amazon.entity.ShoppingItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {
        ItemDetailMapper.class
})
public interface ShoppingItemMapper {

    ShoppingItemMapper INSTANCE = Mappers.getMapper(ShoppingItemMapper.class);

    @Mappings({
            @Mapping(source = "item", target = "itemDetailDto")
    })
    ShoppingItemDto map(ShoppingItem source);

    List<ShoppingItemDto> map(List<ShoppingItem> source);
}
