package com.portfolio.srv.utils;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) {
        switch (username) {
            case "admin":
                return User.builder()
                        .username("admin")
                        .password("{noop}qwerty") // {noop} es para desactivar el hashing
                        .roles("ADMIN", "USER") // Roles que el usuario admin tendrá
                        .build();
            case "user":
                return User.builder()
                        .username("user")
                        .password("{noop}123456")
                        .roles("USER")
                        .build();
            default:
                throw new RuntimeException("Usuario no encontrado");
        }
    }
}