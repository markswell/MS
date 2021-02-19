package com.markswell.auth.jwt;

import com.markswell.auth.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Service
public class JwtTokenProvider {

    @Autowired
    private JwtConfig jwtConfig;

    @Qualifier("userService")
    @Autowired
    private UserDetailsService userDetailsService;

    private String secret;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(getSecretBytes());
    }

    public String createToken(String userName, List<String> roles) {
        var claims = Jwts.claims().setSubject(userName);
        claims.put("roles", roles);
        Date expire = new Date(getExpiresDate());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expire)
                .signWith(HS256, secret).compact();
    }

    public Authentication getAuthentication() {
        return null;
    }

    private long getExpiresDate() {
        return new Date().getTime() + jwtConfig.getExpireLength();
    }

    private byte[] getSecretBytes() {
        return jwtConfig.getSecretKey().getBytes();
    }
}
