package com.example.bill.repository;

import com.example.bill.entity.Bill;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface BillRepository extends CrudRepository<Bill, UUID> {

    Optional<Bill> getBillsByAccountId(UUID id);
}
