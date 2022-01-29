package com.markswell.cadastro.message;

public interface AmqpSendMessage<T> {
    void send(T t);
}
