package com.example.withdraw.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawResponseDto {

    private UUID id;

    private BigDecimal amount;

    private UUID billId;

    private Date dateOfCreate;

    private String email;
}
