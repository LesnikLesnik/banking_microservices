package com.example.bill.entity;

import jakarta.persistence.*;
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
    @Column(name = "id")
    private UUID id;

    @Column(name = "account_id")
    private UUID accountId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "isDefault")
    private Boolean isDefault;

    @Column(name = "creationDate")
    private Date creationDate;

    @Column(name = "overdraftEnabled")
    private Boolean overdraftEnabled;
}
