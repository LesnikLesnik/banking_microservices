package com.example.withdraw.repository;

import com.example.withdraw.entity.Withdraw;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WithdrawRepository extends JpaRepository<Withdraw, UUID> {

    Page<Withdraw> findAllByBillId(UUID id, Pageable pageable);
}
