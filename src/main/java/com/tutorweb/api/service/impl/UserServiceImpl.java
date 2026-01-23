package com.tutorweb.api.service.impl;

import com.tutorweb.api.exception.AppException;
import com.tutorweb.api.exception.ErrorCode;
import com.tutorweb.api.model.dto.request.UpdateUserRequest;
import com.tutorweb.api.model.dto.response.UserResponse;
import com.tutorweb.api.model.entity.User;
import com.tutorweb.api.repository.UserRepository;
import com.tutorweb.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserResponse updateProfile(UpdateUserRequest updateUserRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userRepository.findByUsername(username).orElseThrow(()-> new AppException(ErrorCode.USR_010));
        if(updateUserRequest.getEmail() != null){
            if((userRepository.findByEmail(updateUserRequest.getEmail()).isPresent()))
                throw new AppException(ErrorCode.USR_009);
            user.setEmail(updateUserRequest.getEmail());
        }
        if(updateUserRequest.getUsername() != null){
            if((userRepository.findByUsername(updateUserRequest.getUsername()).isPresent()))
                throw new AppException(ErrorCode.USR_007);
            user.setUsername(updateUserRequest.getUsername());
        }
        if(updateUserRequest.getPhone() != null){
            if((userRepository.findByPhone(updateUserRequest.getPhone()).isPresent()))
                throw new AppException(ErrorCode.USR_008);
            user.setPhone(updateUserRequest.getPhone());
        }
        userRepository.save(user);
        return UserResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .phone(user.getPhone())
                .build();
    }
}
