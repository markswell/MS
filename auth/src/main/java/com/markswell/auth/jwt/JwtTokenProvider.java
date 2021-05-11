package com.markswell.auth.jwt;

import java.util.Date;
import java.util.List;
import java.util.Base64;
import io.jsonwebtoken.*;
import javax.annotation.PostConstruct;
import com.markswell.auth.config.JwtConfig;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;

@Service
public class JwtTokenProvider {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private UserDetailsService userService;

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
        var userDetails = userService.loadUserByUsername(getUserNameFromToken(token));
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
    
    public Boolean validateToken(String token) {
        try {
            var jwt = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            if(jwt.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch(JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private long getExpiresDate() {
        return new Date().getTime() + jwtConfig.getExpireLength();
    }

    private byte[] getSecretBytes() {
        return jwtConfig.getSecretKey().getBytes();
    }
}
