package com.tutorweb.api.service.impl;

import com.tutorweb.api.model.entity.User;
import com.tutorweb.api.repository.UserRepository;
import com.tutorweb.api.security.CustomUserDetail;
import com.tutorweb.api.service.JwtService;
import com.tutorweb.api.type.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.tutorweb.api.type.TokenType.ACCESS_TOKEN;
import static com.tutorweb.api.type.TokenType.REFRESH_TOKEN;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    @Value("${spring.jwt.expiration}")
    private Long expiration ;
    @Value("${spring.jwt.expirationDate}")
    private Long expirationDate ;
    @Value("${spring.jwt.secretKey}")
    private String secretKey ;
    @Value("${spring.jwt.refreshKey}")
    private String refreshKey ;

    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> {
        User user = userRepository.findByUsername(username)
                                  .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUserDetail(user);
        };
    }


    @Override
    public String extractUsername(String token, TokenType tokenType) {
        return extractClaim(token, tokenType, Claims::getSubject);
    }
    @Override
    public List<String> extractRoles(String token, TokenType type) {
        Claims claims = extractAllClaims(token, type);
        return claims.get("roles", List.class);
    }

    @Override
    public boolean isValid(String token, TokenType tokenType, UserDetails userDetails) {
        final String username = extractUsername(token, tokenType);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token, tokenType));
    }
    @Override
    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities()
                                        .stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .collect(Collectors.toList()));
        return buildToken(claims, userDetails, ACCESS_TOKEN, expiration);
    }
    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities()
                                        .stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .collect(Collectors.toList()));
        return buildToken(claims, userDetails, REFRESH_TOKEN, expirationDate);
    }


    private String buildToken(Map<String, Object> claims, UserDetails userDetails, TokenType tokenType, long expirationMs) {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
            .signWith(getKey(tokenType), SignatureAlgorithm.HS256)
            .compact();
    }
    private Key getKey(TokenType tokenType) {
        byte[] keyBytes ;
        if (ACCESS_TOKEN.equals(tokenType)) {
            keyBytes = Base64.getDecoder().decode(secretKey);
        } else {
            keyBytes = Base64.getDecoder().decode(refreshKey);
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private <T> T extractClaim(String token, TokenType tokenType, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token, tokenType);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token, TokenType tokenType) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey(tokenType))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Boolean isTokenExpired(String token , TokenType tokenType) {
        return extractExpiration(token, tokenType).before(new Date());
    }
    private Date extractExpiration(String token , TokenType tokenType) {
        return extractClaim(token, tokenType, Claims::getExpiration);
    }

}
