package com.example.bill.controller;

import com.example.bill.dto.BillRequestDto;
import com.example.bill.dto.BillResponseDto;
import com.example.bill.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @PostMapping("/create")
    public UUID create(@RequestBody BillRequestDto billRequestDto){
        return billService.createBill(billRequestDto);
    }

    @GetMapping("/{id}")
    public BillResponseDto getBill(@PathVariable UUID id){
        return billService.getBillById(id);
    }


    @DeleteMapping("/delete/{id}")
    public void deleteAccount(@PathVariable UUID id){
        billService.deleteBill(id);
    }
}
