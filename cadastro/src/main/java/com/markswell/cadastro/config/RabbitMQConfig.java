package com.markswell.cadastro.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("cadastro.rabbitmq")
public class RabbitMQConfig {

    private String exchenge;
    private String routingkey;
    private String deadLetter;

}
