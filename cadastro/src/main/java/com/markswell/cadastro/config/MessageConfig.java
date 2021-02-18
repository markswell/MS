package com.markswell.cadastro.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

@Configuration
public class MessageConfig {

    @Autowired
    private RabbitMQConfig rabbitMQConfig;

    @Bean
    public Exchange getExchange() {
        return ExchangeBuilder
                .directExchange(rabbitMQConfig.getExchage())
                .durable(true)
                .build();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
