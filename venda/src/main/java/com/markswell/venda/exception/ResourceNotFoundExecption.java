package com.markswell.venda.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class ResourceNotFoundExecption extends RuntimeException {

    private static final long serialVersionUID = -5253483918954939808L;

    public ResourceNotFoundExecption(String message) {
        super(message);
    }

}
