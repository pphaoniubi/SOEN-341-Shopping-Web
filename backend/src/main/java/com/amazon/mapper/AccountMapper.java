package com.amazon.mapper;

import com.amazon.dto.AccountDto;
import com.amazon.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDto map(Account source);

    List<AccountDto> map(List<Account> source);
}
