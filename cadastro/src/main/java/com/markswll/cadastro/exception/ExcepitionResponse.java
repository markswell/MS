package com.markswll.cadastro.exception;

import lombok.Data;
import java.util.Date;
import lombok.Builder;
import java.io.Serializable;

@Data
@Builder
public class ExcepitionResponse implements Serializable {

    private static final long serialVersionUID = -8692613413676762310L;

    private Date timestamp;
    private String message;
    private String detail;

}
