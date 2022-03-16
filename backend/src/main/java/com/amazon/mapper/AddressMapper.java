package com.amazon.mapper;

import com.amazon.dto.AddressDto;
import com.amazon.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressDto map(Address source);

    List<AddressDto> map(List<Address> source);
}
