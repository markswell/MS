package com.markswell.auth.config;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Component
@ConfigurationProperties("jwt")
public class SecurityConfig {
    private String keyStorePath;
    private String secretKey;
    private String keyPairAlias;
}
