package com.example.deposit.controller;

import com.example.deposit.dto.DepositRequestDto;
import com.example.deposit.dto.DepositResponseDto;
import com.example.deposit.service.DepositService;
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
