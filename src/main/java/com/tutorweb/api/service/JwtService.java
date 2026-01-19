package com.tutorweb.api.service;

import com.tutorweb.api.type.TokenType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

public interface JwtService {
    String generateAccessToken(UserDetails userDetails);
    String generateRefreshToken(UserDetails userDetails);
    String extractUsername(String token, TokenType tokenType);
    boolean isValid(String token, TokenType tokenType, UserDetails userDetails);



    UserDetailsService userDetailsService();

}
