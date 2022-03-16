package com.amazon.mapper;

import com.amazon.dto.OrderHistoryDto;
import com.amazon.entity.OrderHistory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderHistoryMapper {

    OrderHistoryMapper INSTANCE = Mappers.getMapper(OrderHistoryMapper.class);

    OrderHistoryDto map(OrderHistory source);

    List<OrderHistoryDto> map(List<OrderHistory> source);
}
