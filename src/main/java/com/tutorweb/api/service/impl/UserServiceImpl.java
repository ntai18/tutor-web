package com.tutorweb.api.service.impl;

import com.tutorweb.api.exception.AppException;
import com.tutorweb.api.exception.ErrorCode;
import com.tutorweb.api.model.dto.request.UpdateUserRequest;
import com.tutorweb.api.model.dto.response.TutorResponse;
import com.tutorweb.api.model.dto.response.UserResponse;
import com.tutorweb.api.model.entity.Tutor;
import com.tutorweb.api.model.entity.User;
import com.tutorweb.api.repository.UserRepository;
import com.tutorweb.api.service.UserService;
import com.tutorweb.api.type.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
                .roleType(user.getRole())
                .build();
    }

    @Override
    public UserResponse getMe() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(()-> new AppException(ErrorCode.USR_010));
        return UserResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .phone(user.getPhone())
                .roleType(user.getRole())
                .build();
    }

    @Override
    public UserResponse approveAdmin() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(()-> new AppException(ErrorCode.USR_010));
        if (user.getRole().equals(RoleType.ADMIN))
            throw new AppException(ErrorCode.AUTH_007);
        user.setRole(RoleType.ADMIN);
        userRepository.save(user);
        return UserResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .phone(user.getPhone())
                .roleType(user.getRole())
                .build();
    }

    @Override
    public UserResponse approveManager() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(()-> new AppException(ErrorCode.USR_010));
        if (user.getRole().equals(RoleType.MANAGER))
            throw new AppException(ErrorCode.AUTH_007);
        user.setRole(RoleType.MANAGER);
        userRepository.save(user);
        return UserResponse.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .phone(user.getPhone())
                .roleType(user.getRole())
                .build();
    }

    @Override
    public List<TutorResponse> getAllTutors() {
        List<User> tutors = userRepository.findTutor(RoleType.TUTOR);
        return tutors.stream().map(user -> TutorResponse.builder()
                                                              .id(user.getTutor().getId())
                                                              .bio(user.getTutor().getBio())
                                                              .experienceYears(user.getTutor().getExperienceYears())
                                                              .hourlyRate(user.getTutor().getHourlyRate())
                                                              .username(user.getUsername())
                                                              .build())
                .toList();
    }
    @Override
    public TutorResponse getTutor(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.TUT_012));
        return TutorResponse.builder()
                .id(user.getTutor().getId())
                .bio(user.getTutor().getBio())
                .experienceYears(user.getTutor().getExperienceYears())
                .hourlyRate(user.getTutor().getHourlyRate())
                .username(user.getUsername())
                .build();
    }

    @Override
    public UserResponse approveTutor(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(()-> new AppException(ErrorCode.USR_010));
        if (!(user.getRole().equals(RoleType.MANAGER) || user.getRole().equals(RoleType.ADMIN)))
            throw new AppException(ErrorCode.AUTH_005);
        User user1 = userRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.USR_010));
        if (user1.getRole().equals(RoleType.TUTOR))
            throw new AppException(ErrorCode.TUT_014);
        user1.setRole(RoleType.TUTOR);
        userRepository.save(user1);
        return UserResponse.builder()
                .email(user1.getEmail())
                .username(user1.getUsername())
                .phone(user1.getPhone())
                .roleType(user1.getRole())
                .build();
    }
}
