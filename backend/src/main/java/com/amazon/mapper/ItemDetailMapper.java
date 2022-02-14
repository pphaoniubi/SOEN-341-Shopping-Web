package com.amazon.mapper;

import com.amazon.dto.ItemDetailDto;
import com.amazon.entity.Item;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ItemDetailMapper {

    ItemDetailMapper INSTANCE = Mappers.getMapper(ItemDetailMapper.class);

    ItemDetailDto map(Item source);

    List<ItemDetailDto> map(List<Item> source);
}
