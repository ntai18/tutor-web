package com.tutorweb.api.security;

import com.tutorweb.api.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.tutorweb.api.type.TokenType.ACCESS_TOKEN;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String path = request.getRequestURI();
        log.info("Path {}", path);
        if(isByPass(path)){
            log.info("url {}", path);
            filterChain.doFilter(request, response);
            return;
        }

        if (authorizationHeader == null && !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authorizationHeader.substring(7);
        final String username = jwtService.extractUsername(token, ACCESS_TOKEN);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            List<String> roles = jwtService.extractRoles(token, ACCESS_TOKEN);
            Collection<GrantedAuthority> authorities = roles.stream().map(
                    SimpleGrantedAuthority::new).collect(Collectors.toList());

            UserDetails userDetails = User.builder()
                                           .username(username)
                                           .password("") // không cần
                                           .authorities(authorities)
                                           .build();

            if (jwtService.isValid(token, ACCESS_TOKEN, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }


        filterChain.doFilter(request, response);

    }
    private boolean isByPass(String uri){
        return uri.startsWith("/api/v1/auth/");
    }
}
