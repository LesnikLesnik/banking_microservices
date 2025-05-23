package com.example.bill.mapper;

import com.example.bill.dto.BillRequestDto;
import com.example.bill.dto.BillResponseDto;
import com.example.bill.entity.Bill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BillMapper {

    BillResponseDto toResponseDto(Bill bill);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", expression = "java(new java.util.Date())")
    Bill toBill(BillRequestDto billRequestDto);
}
