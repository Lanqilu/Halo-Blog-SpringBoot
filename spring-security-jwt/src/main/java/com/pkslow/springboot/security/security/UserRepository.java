package com.pkslow.springboot.security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class UserRepository {

    private static final Map<String, User> allUsers = new HashMap<>();

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    protected void init() {
        allUsers.put("pkslow", new User("pkslow", passwordEncoder.encode("123456"), Collections.singletonList("ROLE_ADMIN")));
        allUsers.put("user", new User("user", passwordEncoder.encode("123456"), Collections.singletonList("ROLE_USER")));
        allUsers.put("1379978893@qq.com", new User("1379978893@qq.com", passwordEncoder.encode("111111"), Collections.singletonList("ROLE_USER")));
    }

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(allUsers.get(username));
    }
}