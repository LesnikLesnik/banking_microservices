package com.example.gateway.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "deposit")
public class Deposit {

    @Id
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "bill_id")
    private UUID billId;

    @Column(name = "dateOfCreate")
    private Date dateOfCreate;

    @Column(name = "email")
    private String email;

    public Deposit(BigDecimal amount, UUID billId, Date dateOfCreate, String email) {
        this.amount = amount;
        this.billId = billId;
        this.dateOfCreate = dateOfCreate;
        this.email = email;
    }
}
