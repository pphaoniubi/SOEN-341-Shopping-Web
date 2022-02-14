package com.amazon.mapper;

import com.amazon.dto.ItemDto;
import com.amazon.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    ItemDto map(Item source);

    List<ItemDto> map(List<Item> source);
}
