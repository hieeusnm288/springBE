package com.example.bespring.service;

import com.example.bespring.domain.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public Account findByUsername (String username);
}
