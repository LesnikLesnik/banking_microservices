package com.example.withdraw.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WithdrawResponseToRabbitDto {

    private BigDecimal amount;

    private String mail;
}
