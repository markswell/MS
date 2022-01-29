package com.markswell.cadastro.message;

import com.markswell.cadastro.config.RabbitMQConfig;
import com.markswell.cadastro.domain.ProdutoVO;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQProdutoSendMessage implements AmqpSendMessage<ProdutoVO> {

    @Autowired
    private RabbitMQConfig config;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void send(ProdutoVO produtoVO) {
        try {
            rabbitTemplate.convertAndSend(config.getExchenge(), config.getRoutingkey(), produtoVO);
        } catch(Exception e) {
            throw new AmqpRejectAndDontRequeueException(e);
        }

    }
}
