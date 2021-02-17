package com.markswll.cadastro.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class ResourceNotFoundExecption extends RuntimeException{

    private static final long serialVersionUID = -2150502172723442645L;

    public ResourceNotFoundExecption(String message) {
        super(message);
    }
}
