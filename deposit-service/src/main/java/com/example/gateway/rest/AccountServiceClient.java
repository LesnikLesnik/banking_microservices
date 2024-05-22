package com.example.gateway.rest;

import com.example.gateway.rest.dto.AccountResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@FeignClient(name = "account-service")
public interface AccountServiceClient {

    @RequestMapping(value = "/api/accounts/{id}", method = RequestMethod.GET)
    AccountResponseDto getAccountById(@PathVariable UUID accountId);
}
