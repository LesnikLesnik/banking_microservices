package com.example.notification.service;

import com.example.notification.config.RabbitMQConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepositMessageHandler {

    private final JavaMailSender javaMailSender;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_DEPOSIT)
    public void receive(Message message) throws JsonProcessingException {
        System.out.println(message);
        byte[] body = message.getBody();
        String jsonBody = new String(body);
        ObjectMapper objectMapper = new ObjectMapper();
        DepositResponseDto depositResponseDto = objectMapper.readValue(jsonBody, DepositResponseDto.class);
        System.out.println(depositResponseDto);

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
}
