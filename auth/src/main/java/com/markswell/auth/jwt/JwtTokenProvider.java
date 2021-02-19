package com.markswell.auth.jwt;

import com.markswell.auth.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
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

    public Authentication getAuthentication(String token) {
        var userDetails = userDetailsService.loadUserByUsername(getUserNameFromToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUserNameFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token != null && token.startsWith("Bearer ")) {
            token = token.substring(7, token.length());
        }
        return token;
    }

    private long getExpiresDate() {
        return new Date().getTime() + jwtConfig.getExpireLength();
    }

    private byte[] getSecretBytes() {
        return jwtConfig.getSecretKey().getBytes();
    }
}
