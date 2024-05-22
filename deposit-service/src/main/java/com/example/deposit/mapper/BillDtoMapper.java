package com.example.deposit.mapper;

import com.example.deposit.rest.dto.BillRequestDto;
import com.example.deposit.rest.dto.BillResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BillDtoMapper {


    BillRequestDto toBillRequestDto(BillResponseDto billResponseDto);
}
