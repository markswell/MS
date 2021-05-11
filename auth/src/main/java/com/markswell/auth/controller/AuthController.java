package com.markswell.auth.controller;

import java.util.HashMap;
import com.markswell.auth.domain.UserVO;
import com.markswell.auth.jwt.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.markswell.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/login")
public class AuthController {

    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserRepository userRepository,
                          JwtTokenProvider jwtTokenProvider,
                          AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping
    public String teste() {
        return "testado";
    }

    @PostMapping(consumes = { "application/json" }, produces = { "application/json" })
    @RequestMapping("auth")
    public ResponseEntity<?> login(@RequestBody UserVO userVO) {
        try {
            authenticationManager.authenticate(getAuthentication(userVO));
            var user = userRepository.findByUserName(userVO.getUserName());

            var token = "";
            if(user != null) {
                token = jwtTokenProvider.createToken(userVO.getUserName(), user.getRoles());
            } else {
                throw new UsernameNotFoundException("Usuário ou senha não correspondem");
            }

            var hashMap = new HashMap<>();
            hashMap.put("username", userVO.getUserName());
            hashMap.put("token", token);
            return ok(hashMap);
        } catch(AuthenticationException e) {
            throw new BadCredentialsException("Usuário ou senha inválido.");
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(UserVO userVO) {
        return new UsernamePasswordAuthenticationToken(userVO.getUserName(), userVO.getPassword());
    }
}
