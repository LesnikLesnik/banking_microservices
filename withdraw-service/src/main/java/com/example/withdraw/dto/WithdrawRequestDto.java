package com.example.withdraw.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawRequestDto {

    private UUID accountId;

    private UUID billId;

    private BigDecimal amount;
}
