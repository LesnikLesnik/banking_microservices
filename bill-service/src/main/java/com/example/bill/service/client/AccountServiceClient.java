package com.example.bill.service.client;

import com.example.bill.dto.account.AccountResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.UUID;

@FeignClient(name = "account-service")
public interface AccountServiceClient {

    @PutMapping("/api/accounts/bill/{id}")
    AccountResponseDto addBillToAccount(@PathVariable UUID id, UUID billId);
}
