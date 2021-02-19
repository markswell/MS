package com.markswell.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("security.jwt.token")
public class JwtConfig {

    private String secretKey;
    private Long expireLength;

}
