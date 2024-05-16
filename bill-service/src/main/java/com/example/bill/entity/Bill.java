package com.example.bill.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bill")
public class Bill {

    @Id
    private UUID id;

    private UUID accountId;

    private BigDecimal amount;

    private Boolean isDefault;

    private Date creationDate;

    private Boolean overdraftEnabled;
}
