package com.example.deposit.controller;

import com.example.deposit.dto.DepositRequestDto;
import com.example.deposit.dto.DepositResponseDto;
import com.example.deposit.dto.DepositResponseToRabbitDto;
import com.example.deposit.service.DepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/deposit")
public class DepositController {

    private final DepositService depositService;

    @PostMapping("/")
    public DepositResponseToRabbitDto deposit(@RequestBody DepositRequestDto depositRequestDto) {
        return depositService.deposit(depositRequestDto.getAccountId(), depositRequestDto.getBillId(), depositRequestDto.getAmount());
    }

    @GetMapping("/bill/{id}")
    public Page<DepositResponseDto> getDepositsByBillId(@PathVariable UUID id, Pageable pageable){
        return depositService.getDepositsByBillId(id, pageable);
    }

    @DeleteMapping("/{id}")
    public UUID deleteDeposit(@PathVariable UUID id){
        return depositService.deleteDeposit(id);
    }

}
