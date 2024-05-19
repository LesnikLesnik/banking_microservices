package com.example.account.controller;

import com.example.account.dto.AccountRequestDto;
import com.example.account.dto.AccountResponseDto;
import com.example.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    public UUID createAccount(@RequestBody AccountRequestDto accountRequestDTO){
        return accountService.createAccount(accountRequestDTO);
    }

    @GetMapping("/{id}")
    public AccountResponseDto getAccount(@PathVariable UUID id){
        return accountService.getAccountById(id);
    }

    @GetMapping("/listAccounts")
    public List<AccountResponseDto> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAccount(@PathVariable UUID id){
        accountService.deleteAccount(id);
    }
}
