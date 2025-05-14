package com.example.notification.service;

import com.example.notification.config.RabbitMQConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepositMessageHandler {

    private final JavaMailSender javaMailSender;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_DEPOSIT)
    public void receiveDeposit(Message message) throws JsonProcessingException {
        log.info("Message to receive {} ", message);
        byte[] body = message.getBody();
        String jsonBody = new String(body);
        ObjectMapper objectMapper = new ObjectMapper();
        DepositResponseDto depositResponseDto = objectMapper.readValue(jsonBody, DepositResponseDto.class);
        log.info("DepositResponseDto {}", depositResponseDto);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(depositResponseDto.getMail());
        mailMessage.setFrom("kazulina.nast@gmail.com");

        mailMessage.setSubject("Deposit");
        mailMessage.setText("Make deposit sum: " + depositResponseDto.getAmount());

        try {
            javaMailSender.send(mailMessage);
        }catch (Exception exception){
            System.out.println(exception);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_WITHDRAW)
    public void receiveWithdraw(Message message) throws JsonProcessingException {
        log.info("Message to receive {} ", message);
        byte[] body = message.getBody();
        String jsonBody = new String(body);
        ObjectMapper objectMapper = new ObjectMapper();
        WithdrawResponseDto withdrawResponseDto = objectMapper.readValue(jsonBody, WithdrawResponseDto.class);
        log.info("WithdrawResponseDto {}", withdrawResponseDto);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(withdrawResponseDto.getMail());
        mailMessage.setFrom("kazulina.nast@gmail.com");

        mailMessage.setSubject("Withdraw");
        mailMessage.setText("Make withdraw sum: " + withdrawResponseDto.getAmount());

        try {
            javaMailSender.send(mailMessage);
        }catch (Exception exception){
            System.out.println(exception);
        }
    }
}
