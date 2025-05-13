package com.example.deposit.rest;

import com.example.deposit.rest.dto.BillRequestDto;
import com.example.deposit.rest.dto.BillResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "bill-service")
public interface BillServiceClient {

    @GetMapping(value = "/api/bills/{id}")
    BillResponseDto getBillById(@PathVariable UUID id);

    @PutMapping(value = "/api/bills/{id}")
    void update(@PathVariable UUID id, BillRequestDto billRequestDto);

    @GetMapping(value = "/api/bills/account/{id}")
    List<BillResponseDto> getBillsByAccountId(@PathVariable UUID id);
}
