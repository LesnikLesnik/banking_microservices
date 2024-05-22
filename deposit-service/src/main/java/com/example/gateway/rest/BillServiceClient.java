package com.example.gateway.rest;

import com.example.gateway.rest.dto.BillRequestDto;
import com.example.gateway.rest.dto.BillResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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
