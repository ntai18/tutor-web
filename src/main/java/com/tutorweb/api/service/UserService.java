package com.tutorweb.api.service;

import com.tutorweb.api.model.dto.request.UpdateUserRequest;
import com.tutorweb.api.model.dto.response.UserResponse;
import com.tutorweb.api.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService {
    UserResponse updateProfile(UpdateUserRequest updateUserRequest);
}
