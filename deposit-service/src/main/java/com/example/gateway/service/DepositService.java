package com.example.gateway.service;

import com.example.gateway.dto.DepositResponseDto;
import com.example.gateway.entity.Deposit;
import com.example.gateway.exception.DepositServiceNullAccountIdException;
import com.example.gateway.mapper.DepositMapper;
import com.example.gateway.repository.DepositRepository;
import com.example.gateway.rest.AccountServiceClient;
import com.example.gateway.rest.BillServiceClient;
import com.example.gateway.rest.dto.AccountResponseDto;
import com.example.gateway.rest.dto.BillRequestDto;
import com.example.gateway.rest.dto.BillResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepositService {

    private final DepositRepository depositRepository;

    private static final String TOPIC_EXCHANGE_DEPOSIT = "js.deposit.notify.exchange";

    private static final String ROUTING_KEY_DEPOSIT = "js.key.deposit";

    private final DepositMapper depositMapper;
    private final AccountServiceClient accountServiceClient;

    private final BillServiceClient billServiceClient;

    private final RabbitTemplate rabbitTemplate;

    public DepositResponseDto deposit(UUID accountId, UUID billId, BigDecimal amount) {
        if (accountId == null){
            throw new DepositServiceNullAccountIdException("Аккаунт с таким id: " + accountId + " не найден");
        }
        if (billId != null){
            BillResponseDto billResponseDto = billServiceClient.getBillById(billId);
            BillRequestDto billRequestDto = createBillRequest(amount, billResponseDto);
            billServiceClient.update(billId, billRequestDto);

            AccountResponseDto accountResponseDto = accountServiceClient.getAccountById(billResponseDto.getAccountId());
            depositRepository.save(new Deposit(amount, billId, new Date(), accountResponseDto.getEmail()));

            return createResponseDto(amount, accountResponseDto);
        }
        BillResponseDto defaultBill = getDefaultBill(accountId);
        BillRequestDto billRequestDto = createBillRequest(amount, defaultBill);
        billServiceClient.update(defaultBill.getId(), billRequestDto);
        AccountResponseDto accountById = accountServiceClient.getAccountById(accountId);
        depositRepository.save(new Deposit(amount, defaultBill.getId(), new Date(), accountById.getEmail()));
        return createResponseDto(amount, accountById);
    }

    private DepositResponseDto createResponseDto(BigDecimal amount, AccountResponseDto accountResponseDto) {
        DepositResponseDto depositResponseDto = new DepositResponseDto(amount, accountResponseDto.getEmail());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            rabbitTemplate
                    .convertAndSend(TOPIC_EXCHANGE_DEPOSIT, ROUTING_KEY_DEPOSIT,
                            objectMapper.writeValueAsString(depositResponseDto));
        } catch (JsonProcessingException e) {
            throw new DepositServiceNullAccountIdException("Не получается отправить сообщение to RabbitMq");
        }
        return depositResponseDto;
    }

    private BillRequestDto createBillRequest(BigDecimal amount, BillResponseDto billResponseDto) {
        BillRequestDto billRequestDto = new BillRequestDto();
        billRequestDto.setAccountId(billResponseDto.getAccountId());
        billRequestDto.setCreationDate(billResponseDto.getCreationDate());
        billRequestDto.setIsDefault(billResponseDto.getIsDefault());
        billRequestDto.setOverdraftEnabled(billResponseDto.getOverdraftEnabled());
        billRequestDto.setAmount(billResponseDto.getAmount().add(amount));
        return billRequestDto;
    }

    private BillResponseDto getDefaultBill(UUID accountId) {
        return billServiceClient.getBillsByAccountId(accountId).stream()
                .filter(BillResponseDto::getIsDefault)
                .findAny()
                .orElseThrow(() -> new RuntimeException("не получается найти базовый счет для id " + accountId));
    }


}

//TODO сделать CRUD методы