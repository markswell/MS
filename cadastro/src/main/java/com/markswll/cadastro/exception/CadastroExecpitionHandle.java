package com.markswll.cadastro.exception;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

import static org.springframework.http.ResponseEntity.*;

@ControllerAdvice
public class CadastroExecpitionHandle extends ResponseEntityExceptionHandler {

    @ExceptionHandler({InvalidDataAccessApiUsageException.class})
    public ResponseEntity<ExcepitionResponse> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException e, WebRequest request) {
        return badRequest().body(getExecptionHandle("Formato de produto inválido.", request));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExcepitionResponse>  handleBadRequestExecption(Exception e, WebRequest request) {
        return badRequest().body(getExecptionHandle(e, request));
    }

    private ExcepitionResponse getExecptionHandle(Exception e, WebRequest request) {
        return ExcepitionResponse.builder()
                            .timestamp(new Date())
                            .message(e.getMessage())
                            .detail(request.getDescription(false))
                            .build();
    }

    private ExcepitionResponse getExecptionHandle(String message, WebRequest request) {
        return ExcepitionResponse.builder()
                .timestamp(new Date())
                .message(message)
                .detail(request.getDescription(false))
                .build();
    }
}
