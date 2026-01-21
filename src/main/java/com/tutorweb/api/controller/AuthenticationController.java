package com.tutorweb.api.controller;

import com.tutorweb.api.model.dto.response.ApiResponse;
import com.tutorweb.api.model.dto.response.UserResponse;
import com.tutorweb.api.model.dto.response.TokenResponse;
import com.tutorweb.api.model.dto.request.LoginRequest;
import com.tutorweb.api.model.dto.request.SignUpRequest;
import com.tutorweb.api.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        ApiResponse<TokenResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(authenticationService.login(loginRequest));
        return apiResponse;
    }
    @PostMapping("/signup")
    public ApiResponse<UserResponse> signup(@RequestBody SignUpRequest signUpRequest) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(authenticationService.signup(signUpRequest));
        return  apiResponse;
    }
    @PostMapping("/refresh")
    public ApiResponse<TokenResponse> refresh(HttpServletRequest request) {
        ApiResponse<TokenResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(authenticationService.refreshToken(request));
        return apiResponse;
    }
}
