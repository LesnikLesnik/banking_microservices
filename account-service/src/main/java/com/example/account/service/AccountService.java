package com.example.account.service;

import com.example.account.dto.AccountRequestDto;
import com.example.account.dto.AccountResponseDto;
import com.example.account.entity.Account;
import com.example.account.exception.AccountNotFoundException;
import com.example.account.mapper.AccountMapper;
import com.example.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    public UUID createAccount(AccountRequestDto accountRequestDTO) {
        Account account = accountMapper.toAccount(accountRequestDTO);
        account.setId(UUID.randomUUID());
        return accountRepository.save(account).getId();
    }

    public AccountResponseDto getAccountById(UUID id) {
        Optional<Account> accountById = accountRepository.findById(id);
        return accountById.map(accountMapper::toResponseDto)
                .orElseThrow(() -> new AccountNotFoundException("Аккаунт с id: " + id + " не найден"));
    }

    public List<AccountResponseDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(accountMapper::toResponseDto)
                .toList();
    }

    public AccountResponseDto updateAccount(UUID id, AccountRequestDto accountRequestDto) {
        Account accountToUpdate = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Аккаунт с id: " + id + " не найден"));
        accountToUpdate.setId(id);
        accountMapper.updateAccountFromDto(accountRequestDto, accountToUpdate);
        Account updatedAccount = accountRepository.save(accountToUpdate);
        return accountMapper.toResponseDto(updatedAccount);
    }


    public void deleteAccount(UUID id) {
        accountRepository.deleteById(id);
    }
}
