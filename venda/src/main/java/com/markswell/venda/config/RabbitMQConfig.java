package com.markswell.venda.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("venda.rabbitmq")
public class RabbitMQConfig {

    private String exchage;
    private String queue;

}
