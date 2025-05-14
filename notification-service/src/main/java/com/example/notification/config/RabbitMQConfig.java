package com.example.notification.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQConfig {

    //Deposit
    public static final String QUEUE_DEPOSIT = "js.deposit.notify";

    private static final String TOPIC_EXCHANGE_DEPOSIT = "js.deposit.notify.exchange";

    private static final String ROUTING_KEY_DEPOSIT = "js.key.deposit";

    @Bean
    public TopicExchange depositExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_DEPOSIT);
    }

    @Bean
    public Queue queueDeposit() {
        return new Queue(QUEUE_DEPOSIT);
    }

    @Bean
    public Binding depositBinding() {
        return BindingBuilder
                .bind(queueDeposit())
                .to(depositExchange())
                .with(ROUTING_KEY_DEPOSIT);
    }

    //Withdraw

    public static final String QUEUE_WITHDRAW = "js.withdraw.notify";

    private static final String TOPIC_EXCHANGE_WITHDRAW = "js.deposit.withdraw.exchange";

    private static final String ROUTING_KEY_WITHDRAW = "js.key.withdraw";

    @Bean
    public TopicExchange withdrawExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_WITHDRAW);
    }

    @Bean
    public Queue queueWithdraw() {
        return new Queue(QUEUE_WITHDRAW);
    }

    @Bean
    public Binding withdrawBinding() {
        return BindingBuilder
                .bind(queueWithdraw())
                .to(withdrawExchange())
                .with(ROUTING_KEY_WITHDRAW);
    }
}
