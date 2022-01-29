package com.markswell.cadastro.message;

import com.markswell.cadastro.config.RabbitMQConfig;
import com.markswell.cadastro.domain.ProdutoVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoSendMessage {

    @Autowired
    private RabbitMQConfig config;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(ProdutoVO produtoVO) {
        rabbitTemplate.convertAndSend(config.getExchenge(), config.getRoutingkey(), produtoVO);

    }
}
