package com.example.withdraw.entity;

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
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "withdraw")
public class Withdraw {

    @Id
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
}
