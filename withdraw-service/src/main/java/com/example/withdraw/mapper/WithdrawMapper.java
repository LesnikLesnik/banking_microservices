package com.example.withdraw.mapper;

import com.example.withdraw.dto.WithdrawResponseDto;
import com.example.withdraw.entity.Withdraw;
import org.mapstruct.Mapper;

@Mapper
public interface WithdrawMapper {

    WithdrawResponseDto toWithdrawResponse(Withdraw withdraw);
}
