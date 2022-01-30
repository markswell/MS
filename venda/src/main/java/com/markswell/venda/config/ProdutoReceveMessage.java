package com.markswell.venda.config;

import com.markswell.venda.converter.VOConverter;
import com.markswell.venda.domain.ProdutoVO;
import com.markswell.venda.entity.Produto;
import com.markswell.venda.repository.ProdutoRepository;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

@Configuration
public class ProdutoReceveMessage {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private VOConverter<Produto, ProdutoVO> voConverter;

    @RabbitListener(queues = {"${venda.rabbitmq.queue}"})
    public void receive(@Payload ProdutoVO produtoVO) {
        try {
            var retorno = produtoRepository.save(voConverter.voParse(produtoVO, Produto.class));
        } catch(Exception e) {
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
