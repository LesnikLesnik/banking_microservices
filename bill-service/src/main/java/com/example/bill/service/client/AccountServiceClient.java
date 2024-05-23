package com.example.bill.service.client;

import com.example.bill.dto.account.AccountResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "account-service")
public interface AccountServiceClient {

    @GetMapping("/api/accounts/{id}")
    AccountResponseDto getAccount(@PathVariable("id") UUID id);
    @PutMapping("/api/accounts/bill/{id}")
    AccountResponseDto addBillToAccount(@PathVariable("id") UUID id, @RequestParam("billId") UUID billId);
}
