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
@RequestMapping("/api/bills")
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

    @GetMapping("/account/{id}")
    public List<BillResponseDto> getBillsByAccountId(@PathVariable("id") UUID id){
        return billService.getBillsByAccountId(id);
    }

    @PutMapping("/{id}")
    public BillResponseDto updateBill(@PathVariable UUID id,
                                      @RequestBody BillRequestDto billRequestDTO) {
       return billService.updateBill(id, billRequestDTO);
    }
    @DeleteMapping("/{id}")
    public UUID deleteBill(@PathVariable UUID id){
        return billService.deleteBill(id);
    }
}
