package com.pkslow.springboot.security.controller;

import com.halo.model.request.AuthRequest;
import com.halo.model.dto.LoginDto;
import com.pkslow.springboot.security.jwt.JwtTokenProvider;
import com.pkslow.springboot.security.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/login/test")
    public String loginTest(@RequestBody LoginDto request) {
        String email = request.getEmail();
        System.out.println(email);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, request.getPassword()));
        String token = jwtTokenProvider.createToken(email, Collections.singletonList("ROLE_USER"));
        return token;
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {
        String username = request.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, request.getPassword()));

        String token = jwtTokenProvider.createToken(username,
                userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found")).getRoles()
        );

        return token;
    }
}
