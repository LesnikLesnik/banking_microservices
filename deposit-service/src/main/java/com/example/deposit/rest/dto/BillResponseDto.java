package com.example.deposit.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillResponseDto {

    private UUID id;

    private UUID accountId;

    private BigDecimal amount;

    private Boolean isDefault;

    private Date creationDate;

    private Boolean overdraftEnabled;
}
