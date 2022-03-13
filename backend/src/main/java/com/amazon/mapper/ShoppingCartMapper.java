package com.amazon.mapper;

import com.amazon.dto.ShoppingCartDto;
import com.amazon.entity.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {
        AccountMapper.class
})
public interface ShoppingCartMapper {

    ShoppingCartMapper INSTANCE = Mappers.getMapper(ShoppingCartMapper.class);

    @Mappings({
            @Mapping(source = "account", target = "accountDto")
    })
    ShoppingCartDto map(ShoppingCart source);
}
