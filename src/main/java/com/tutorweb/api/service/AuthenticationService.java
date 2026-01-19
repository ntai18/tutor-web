package com.tutorweb.api.service;

import com.tutorweb.api.model.dto.response.SignUpResponse;
import com.tutorweb.api.model.dto.response.TokenResponse;
import com.tutorweb.api.model.dto.request.LoginRequest;
import com.tutorweb.api.model.dto.request.SignUpRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    TokenResponse login(LoginRequest loginRequest);
    SignUpResponse signup(SignUpRequest signInRequest);
    TokenResponse refreshToken(HttpServletRequest request);
}
