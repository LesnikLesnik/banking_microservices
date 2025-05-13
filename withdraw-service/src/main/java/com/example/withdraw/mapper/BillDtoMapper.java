package com.example.withdraw.mapper;

import com.example.withdraw.rest.dto.BillRequestDto;
import com.example.withdraw.rest.dto.BillResponseDto;
import org.mapstruct.Mapper;

@Mapper
public interface BillDtoMapper {

    BillRequestDto toBillRequestDto(BillResponseDto billResponseDto);
}
