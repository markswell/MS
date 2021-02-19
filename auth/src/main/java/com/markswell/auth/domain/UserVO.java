package com.markswell.auth.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserVO implements Serializable {

    private static final long serialVersionUID = 5501640156787721699L;

    private String userName;
    private String password;
}
