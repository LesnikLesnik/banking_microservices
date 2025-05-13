package com.example.withdraw.service;

import com.example.withdraw.dto.WithdrawResponseDto;
import com.example.withdraw.dto.WithdrawResponseToRabbitDto;
import com.example.withdraw.entity.Withdraw;
import com.example.withdraw.exception.WithDrawServiceException;
import com.example.withdraw.mapper.BillDtoMapper;
import com.example.withdraw.mapper.WithdrawMapper;
import com.example.withdraw.repository.WithdrawRepository;
import com.example.withdraw.rest.AccountServiceClient;
import com.example.withdraw.rest.BillServiceClient;
import com.example.withdraw.rest.dto.AccountResponseDto;
import com.example.withdraw.rest.dto.BillRequestDto;
import com.example.withdraw.rest.dto.BillResponseDto;
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
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WithdrawService {

    private final WithdrawRepository withdrawRepository;
    private final WithdrawMapper withdrawMapper;
    private final BillDtoMapper billDtoMapper;
    private final BillServiceClient billServiceClient;
    private final AccountServiceClient accountServiceClient;
    private final RabbitTemplate rabbitTemplate;
    private static final String TOPIC_EXCHANGE_WITHDRAW = "js.deposit.withdraw.exchange";
    private static final String ROUTING_KEY_WITHDRAW = "js.key.withdraw";



    public WithdrawResponseDto getWithdrawById(UUID id) {
        Withdraw withdraw = withdrawRepository.findById(id)
                .orElseThrow(() -> new WithDrawServiceException("Вывод средств с id: " + id + " не найден"));
        return withdrawMapper.toWithdrawResponse(withdraw);
    }

    public Page<WithdrawResponseDto> getWithdrawsByBillId(UUID id, Pageable pageable) {
        Page<Withdraw> withdraws = withdrawRepository.findAllByBillId(id, pageable);
        return withdraws.map(withdrawMapper::toWithdrawResponse);
    }

    @Transactional
    public WithdrawResponseToRabbitDto withdraw(UUID accountId, UUID billId, BigDecimal amount){
        if (accountId == null && billId == null){
            throw new WithDrawServiceException("Аккаунт и счет не заданы");
        }

        if (billId != null){
            log.info("Start withdraw from bill with id {}", billId);
            BillResponseDto billResponseDto = billServiceClient.getBillById(billId);
            BillRequestDto billRequestDto = billDtoMapper.toBillRequestDto(billResponseDto);
            log.info("Initial balance: {}, sum to subtract {}", billRequestDto.getAmount(), amount);

            billRequestDto.setAmount(billResponseDto.getAmount().subtract(amount));
            billServiceClient.update(billId, billRequestDto);
            log.info("Bill`s amount update successful, end balance: {}", billRequestDto.getAmount());

            AccountResponseDto accountResponseDto = accountServiceClient.getAccountById(accountId);
            withdrawRepository.save(new Withdraw(UUID.randomUUID(), amount, billId, new Date(), accountResponseDto.getEmail()));
            log.info("The withdraw was successful");

            return createResponseDto(amount, accountResponseDto);
        }

        log.info("Search default bill to account with id: {}", accountId);
        BillResponseDto defaultBill = getDefaultBill(accountId);
        BillRequestDto billRequestDto = billDtoMapper.toBillRequestDto(defaultBill);
        log.info("Initial balance: {}, sum to subtract {}", billRequestDto.getAmount(), amount);

        billRequestDto.setAmount(defaultBill.getAmount().subtract(amount));
        billServiceClient.update(defaultBill.getId(), billRequestDto);
        log.info("Bill`s amount update successful, end balance: {}", billRequestDto.getAmount());

        AccountResponseDto accountResponseDto = accountServiceClient.getAccountById(accountId);
        withdrawRepository.save(new Withdraw(UUID.randomUUID(), amount, defaultBill.getId(), new Date(), accountResponseDto.getEmail()));
        log.info("The withdraw was successful");

        return createResponseDto(amount, accountResponseDto);
    }

    private WithdrawResponseToRabbitDto createResponseDto(BigDecimal amount, AccountResponseDto accountResponseDto) {
        WithdrawResponseToRabbitDto withdrawResponse = new WithdrawResponseToRabbitDto(amount, accountResponseDto.getEmail());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            rabbitTemplate
                    .convertAndSend(TOPIC_EXCHANGE_WITHDRAW, ROUTING_KEY_WITHDRAW,
                            objectMapper.writeValueAsString(withdrawResponse));
        } catch (JsonProcessingException e) {
            throw new WithDrawServiceException("Не получается отправить сообщение to RabbitMq");
        }
        return withdrawResponse;
    }

    private BillResponseDto getDefaultBill(UUID accountId) {
        return billServiceClient.getBillsByAccountId(accountId)
                .stream()
                .filter(BillResponseDto::getIsDefault)
                .findAny()
                .orElseThrow(() -> new WithDrawServiceException("Can`t find default bill to account with id: " + accountId));
    }


}
