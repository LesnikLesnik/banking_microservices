package com.example.bill.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
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
