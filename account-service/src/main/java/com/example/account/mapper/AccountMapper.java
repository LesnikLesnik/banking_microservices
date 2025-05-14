package com.example.account.mapper;

import com.example.account.dto.AccountRequestDto;
import com.example.account.dto.AccountResponseDto;
import com.example.account.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AccountMapper {

    AccountResponseDto toResponseDto(Account account);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", expression = "java(new java.util.Date())")
    Account toAccount(AccountRequestDto accountRequestDTO);

}

