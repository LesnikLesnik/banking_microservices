package com.example.withdraw.controller;

import com.example.withdraw.dto.WithdrawRequestDto;
import com.example.withdraw.dto.WithdrawResponseDto;
import com.example.withdraw.dto.WithdrawResponseToRabbitDto;
import com.example.withdraw.service.WithdrawService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/withdraw")
public class WithdrawController {

    private final WithdrawService withdrawService;

    @GetMapping("/{id}")
    public WithdrawResponseDto getWithdrawById(@PathVariable UUID id) {
        return withdrawService.getWithdrawById(id);
    }

    @PostMapping("/")
    public WithdrawResponseToRabbitDto withdraw(@RequestBody WithdrawRequestDto withdrawRequestDto) {
        return withdrawService.withdraw(withdrawRequestDto.getAccountId(), withdrawRequestDto.getBillId(), withdrawRequestDto.getAmount());
    }

    @GetMapping("/bill/{id}")
    public Page<WithdrawResponseDto> getWithdrawsByBillId(@PathVariable UUID id, Pageable pageable) {
        return withdrawService.getWithdrawsByBillId(id, pageable);
    }
}
