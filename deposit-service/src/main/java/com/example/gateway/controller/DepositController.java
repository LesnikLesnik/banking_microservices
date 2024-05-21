package com.example.gateway.controller;

import com.example.gateway.dto.DepositRequestDto;
import com.example.gateway.dto.DepositResponseDto;
import com.example.gateway.service.DepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/deposit")
public class DepositController {

    private final DepositService depositService;

    @PostMapping("/")
    public DepositResponseDto deposit(@RequestBody DepositRequestDto depositRequestDto) {
        return depositService.deposit(depositRequestDto.getAccountId(), depositRequestDto.getBillId(), depositRequestDto.getAmount());
    }
}
