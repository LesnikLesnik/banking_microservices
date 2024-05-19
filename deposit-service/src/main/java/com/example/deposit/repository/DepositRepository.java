package com.example.deposit.repository;


import com.example.deposit.entity.Deposit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepositRepository extends JpaRepository<Deposit, UUID> {

    Page<Deposit> findAllByBillId(UUID id, Pageable pageable);
}
