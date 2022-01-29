package com.markswell.cadastro.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MessageConfig {

    @Autowired
    private RabbitMQConfig rabbitMQConfig;

    @Autowired
    public ConnectionFactory connectionFactory;

    @Bean
    public DirectExchange getDirectExchange() {
        return new DirectExchange(rabbitMQConfig.getExchenge());
    }

    @Bean
    public Queue getDeadLetter() {
        return new Queue(rabbitMQConfig.getDeadLetter());
    }

    @Bean
    public Queue getQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", rabbitMQConfig.getExchenge());
        args.put("x-dead-letter-routing-key", rabbitMQConfig.getDeadLetter());
        return new Queue(rabbitMQConfig.getRoutingkey(), true, false, false, args);
    }

    @Bean
    public Binding bindingQueue() {
        return BindingBuilder.bind(getQueue()).to(getDirectExchange()).with(rabbitMQConfig.getRoutingkey());
    }

    @Bean
    public Binding bindingDeadLetter() {
        return BindingBuilder.bind(getDeadLetter()).to(getDirectExchange()).with(rabbitMQConfig.getDeadLetter());
    }

    public SimpleRabbitListenerContainerFactory getSimpleRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory =  new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory( connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        return factory;
    }

    @Bean
    public RabbitTemplate getRabbitTemplete() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate( connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Exchange getExchange() {
        return ExchangeBuilder
                .directExchange(rabbitMQConfig.getExchenge())
                .durable(true)
                .build();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
