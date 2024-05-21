package com.example.gateway.repository;


import com.example.gateway.entity.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepositRepository extends JpaRepository<Deposit, UUID> {

}
