package com.example.gateway.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class DepositRequestDto {

    private UUID accountId;

    private UUID billId;

    private BigDecimal amount;

}
