package com.example.bill.repository;

import com.example.bill.entity.Bill;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface BillRepository extends CrudRepository<Bill, UUID> {

    List<Bill> getBillsByAccountId(UUID id);
}
