package com.example.gateway.rest;

import com.example.gateway.rest.dto.BillRequestDto;
import com.example.gateway.rest.dto.BillResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "bill-service")
public interface BillServiceClient {

    @RequestMapping(value = "/api/bills/{id}", method = RequestMethod.GET)
    BillResponseDto getBillById(@PathVariable("id") UUID billId);

    @RequestMapping(value = "/api/bills/{id}", method = RequestMethod.PUT)
    void update(@PathVariable("id") UUID id, BillRequestDto billRequestDto);

    @RequestMapping(value = "/api/bills/account/{id}", method = RequestMethod.GET)
    List<BillResponseDto> getBillsByAccountId(@PathVariable("id") UUID id);
}

//TODO убрать RequestMapping  и заменить в соответствии с методами