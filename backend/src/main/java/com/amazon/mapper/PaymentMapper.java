package com.amazon.mapper;

import com.amazon.dto.PaymentDto;
import com.amazon.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    PaymentDto map(Payment source);

    List<PaymentDto> map(List<Payment> source);
}
