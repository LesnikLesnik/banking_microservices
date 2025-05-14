package com.example.deposit.mapper;

import com.example.deposit.dto.DepositResponseDto;
import com.example.deposit.entity.Deposit;
import org.mapstruct.Mapper;

@Mapper
public interface DepositMapper {

    DepositResponseDto toDepositResponseDto(Deposit deposit);
}
