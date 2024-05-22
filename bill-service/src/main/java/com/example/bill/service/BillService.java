package com.example.bill.service;

import com.example.bill.dto.BillRequestDto;
import com.example.bill.dto.BillResponseDto;
import com.example.bill.entity.Bill;
import com.example.bill.exception.BillNotFoundException;
import com.example.bill.mapper.BillMapper;
import com.example.bill.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;

    private final BillMapper billMapper;

    public BillResponseDto getBillById(UUID id) {
        Optional<Bill> billById = billRepository.findById(id);
        return billById.map(billMapper::toResponseDto)
                .orElseThrow(() -> new BillNotFoundException("Счет с id " + id + " не найден"));
    }

    public UUID createBill(BillRequestDto billRequestDto) {
        Bill bill = billMapper.toBill(billRequestDto);
        bill.setId(UUID.randomUUID());
        return billRepository.save(bill).getId();
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
