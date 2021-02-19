package com.markswell.auth.service;

import com.markswell.auth.entity.User;
import com.markswell.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.Serializable;

import static java.lang.String.format;

@Service
public class UserService implements UserDetailsService, Serializable {

    private static final long serialVersionUID = 5927786353436853668L;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        var user = userRepository.findByUserName(s);
        if(user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException(format("Usuário %s não foi encontrado.", s));
        }
    }
}
