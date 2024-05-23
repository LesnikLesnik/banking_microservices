package com.example.bill.service;

import com.example.bill.dto.BillRequestDto;
import com.example.bill.dto.BillResponseDto;
import com.example.bill.entity.Bill;
import com.example.bill.exception.BillNotFoundException;
import com.example.bill.mapper.BillMapper;
import com.example.bill.repository.BillRepository;
import com.example.bill.service.client.AccountServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;

    private final BillMapper billMapper;

    private final AccountServiceClient accountServiceClient;

    public BillResponseDto getBillById(UUID id) {
        Optional<Bill> billById = billRepository.findById(id);
        return billById.map(billMapper::toResponseDto)
                .orElseThrow(() -> new BillNotFoundException("Счет с id " + id + " не найден"));
    }

    @Transactional
    public UUID createBill(BillRequestDto billRequestDto) {
        Bill bill = billMapper.toBill(billRequestDto);
        bill.setId(UUID.randomUUID());
        log.info("Create bill completed {}", bill);
        UUID savedBillId = billRepository.save(bill).getId();

        accountServiceClient.addBillToAccount(billRequestDto.getAccountId(), savedBillId);
        log.info("Add bill to account with id {}", savedBillId);
        return savedBillId;
    }


    public BillResponseDto updateBill(UUID id, BillRequestDto billRequestDto) {
        Bill bill = billMapper.toBill(billRequestDto);
        bill.setId(id);
        billRepository.save(bill);
        return billMapper.toResponseDto(bill);
    }

    public UUID deleteBill(UUID id) {
        billRepository.deleteById(id);
        return id;
    }

    public List<BillResponseDto> getBillsByAccountId(UUID id) {
        List<Bill> billsByAccount = billRepository.getBillsByAccountId(id);
        return billsByAccount
                .stream()
                .map(billMapper::toResponseDto)
                .toList();
    }

}
