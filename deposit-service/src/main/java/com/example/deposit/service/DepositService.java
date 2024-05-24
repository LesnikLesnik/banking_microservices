package com.example.deposit.service;

import com.example.deposit.dto.DepositResponseToRabbitDto;
import com.example.deposit.dto.DepositResponseDto;
import com.example.deposit.entity.Deposit;
import com.example.deposit.exception.DepositServiceException;
import com.example.deposit.mapper.BillDtoMapper;
import com.example.deposit.mapper.DepositMapper;
import com.example.deposit.repository.DepositRepository;
import com.example.deposit.rest.AccountServiceClient;
import com.example.deposit.rest.BillServiceClient;
import com.example.deposit.rest.dto.AccountResponseDto;
import com.example.deposit.rest.dto.BillRequestDto;
import com.example.deposit.rest.dto.BillResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepositService {

    private final DepositRepository depositRepository;

    private static final String TOPIC_EXCHANGE_DEPOSIT = "js.deposit.notify.exchange";

    private static final String ROUTING_KEY_DEPOSIT = "js.key.deposit";

    private final BillDtoMapper billDtoMapper;

    private final DepositMapper depositMapper;
    private final AccountServiceClient accountServiceClient;

    private final BillServiceClient billServiceClient;

    private final RabbitTemplate rabbitTemplate;

    public Page<DepositResponseDto> getDepositsByBillId(UUID billId, Pageable pageable) {
        Page<Deposit> deposits = depositRepository.findAllByBillId(billId, pageable);
        return deposits.map(depositMapper::toDepositResponseDto);
    }

    @Transactional
    public DepositResponseToRabbitDto deposit(UUID accountId, UUID billId, BigDecimal amount) {
        if (accountId == null && billId == null) {
            throw new DepositServiceException("Аккаунт и счет не заданы");
        }
        if (billId != null) {
            log.info("Start deposit to bill with id {}", billId);
            BillResponseDto billResponseDto = billServiceClient.getBillById(billId);
            BillRequestDto billRequestDto = billDtoMapper.toBillRequestDto(billResponseDto);
            log.info("Initial balance: {}, sum to add {}", billRequestDto.getAmount(), amount);
            billRequestDto.setAmount(billResponseDto.getAmount().add(amount));

            billServiceClient.update(billId, billRequestDto);
            log.info("Bill`s amount update successful, end balance: {}", billRequestDto.getAmount());

            AccountResponseDto accountResponseDto = accountServiceClient.getAccountById(billResponseDto.getAccountId());
            depositRepository.save(new Deposit(UUID.randomUUID(), amount, billId, new Date(), accountResponseDto.getEmail()));
            log.info("The deposit was successful");

            return createResponseDto(amount, accountResponseDto);
        }
        log.info("Search default bill to account with id: {}", accountId);
        BillResponseDto defaultBill = getDefaultBill(accountId);
        BillRequestDto billRequestDto = billDtoMapper.toBillRequestDto(defaultBill);
        log.info("Initial balance: {}", billRequestDto.getAmount());
        billRequestDto.setAmount(defaultBill.getAmount().add(amount));

        billServiceClient.update(defaultBill.getId(), billRequestDto);
        log.info("Bill`s amount update successful, end balance: {}", billRequestDto.getAmount());

        AccountResponseDto accountById = accountServiceClient.getAccountById(accountId);
        depositRepository.save(new Deposit(UUID.randomUUID(), amount, defaultBill.getId(), new Date(), accountById.getEmail()));
        log.info("The deposit was successful");

        return createResponseDto(amount, accountById);
    }

    private DepositResponseToRabbitDto createResponseDto(BigDecimal amount, AccountResponseDto accountResponseDto) {
        DepositResponseToRabbitDto depositResponseToRabbitDto = new DepositResponseToRabbitDto(amount, accountResponseDto.getEmail());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            rabbitTemplate
                    .convertAndSend(TOPIC_EXCHANGE_DEPOSIT, ROUTING_KEY_DEPOSIT,
                            objectMapper.writeValueAsString(depositResponseToRabbitDto));
        } catch (JsonProcessingException e) {
            throw new DepositServiceException("Не получается отправить сообщение to RabbitMq");
        }
        return depositResponseToRabbitDto;
    }

    private BillResponseDto getDefaultBill(UUID accountId) {
        return billServiceClient.getBillsByAccountId(accountId).stream()
                .filter(BillResponseDto::getIsDefault)
                .findAny()
                .orElseThrow(() -> new DepositServiceException("The default bill was not found for id " + accountId));
    }

    public UUID deleteDeposit(UUID id) {
        Optional<Deposit> depositOptional = depositRepository.findById(id);
        if (depositOptional.isEmpty()) {
            throw new DepositServiceException("Депозит с id: " + id + " не найден");
        }
        Deposit deposit = depositOptional.get();
        UUID billId = deposit.getBillId();
        BigDecimal depositAmount = deposit.getAmount();
        log.info("Deposit for delete: {}", deposit);

        BillResponseDto billResponseDto = billServiceClient.getBillById(billId);
        if (billResponseDto == null) {
            throw new DepositServiceException("Счет с id: " + billId + " не найден");
        }
        BillRequestDto billRequestDto = billDtoMapper.toBillRequestDto(billResponseDto);
        billRequestDto.setAmount(billResponseDto.getAmount().subtract(depositAmount));
        log.info("Bill for delete deposit: {}", billResponseDto);

        billServiceClient.update(billId, billRequestDto);
        depositRepository.deleteById(id);

        log.info("Deposit with id: {} successfully deleted and bill updated", id);
        return id;
    }
}
