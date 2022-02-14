package com.amazon.mapper;

import com.amazon.dto.ItemListDto;
import com.amazon.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ItemListMapper {

    ItemListMapper INSTANCE = Mappers.getMapper(ItemListMapper.class);

    ItemListDto map(Item source);

    List<ItemListDto> map(List<Item> source);
}
