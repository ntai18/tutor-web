package com.tutorweb.api.service.impl;

import com.tutorweb.api.exception.AppException;
import com.tutorweb.api.exception.ErrorCode;
import com.tutorweb.api.model.dto.request.UpdateUserRequest;
import com.tutorweb.api.model.dto.response.TutorResponse;
import com.tutorweb.api.model.dto.response.UserResponse;
import com.tutorweb.api.model.entity.Tutor;
import com.tutorweb.api.model.entity.User;
import com.tutorweb.api.repository.TutorRepository;
import com.tutorweb.api.repository.UserRepository;
import com.tutorweb.api.service.UserService;
import com.tutorweb.api.type.RoleType;
import com.tutorweb.api.type.StatusType;
import com.tutorweb.api.type.UserStatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TutorRepository tutorRepository;

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
                .userStatusType(user.getStatus())
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
                .userStatusType(user.getStatus())
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
                .userStatusType(user.getStatus())
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
                .userStatusType(user.getStatus())
                .build();
    }

    @Override
    public List<TutorResponse> getAllTutors() {
        List<Tutor> tutors = tutorRepository.findTutorApproved();
        return tutors.stream().map(tutor -> TutorResponse.builder()
                        .id(tutor.getId())
                        .status(tutor.getStatus())
                        .bio(tutor.getBio())
                        .status(tutor.getStatus())
                        .experienceYears(tutor.getExperienceYears())
                        .username(tutor.getUser().getUsername())
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
        Tutor tutor = tutorRepository.findByTutorUser(id).orElseThrow(()-> new AppException(ErrorCode.TUT_012));
        if((tutor.getUser().getRole().equals(RoleType.TUTOR)) && ((tutor.getStatus()) != (StatusType.PENDING)))
            throw new AppException(ErrorCode.TUT_014);
        tutor.getUser().setRole(RoleType.TUTOR);
        tutor.setStatus(StatusType.APPROVED);
        tutorRepository.save(tutor);
        return UserResponse.builder()
                .roleType(tutor.getUser().getRole())
                .email(tutor.getUser().getEmail())
                .username(tutor.getUser().getUsername())
                .phone(tutor.getUser().getPhone())
                .userStatusType(tutor.getUser().getStatus())
                .build();
    }

    @Override
    public List<TutorResponse> getTutorPending() {
        List<Tutor> tutors = tutorRepository.findTutorPending();
        return tutors.stream().map(tutor -> TutorResponse.builder()
                .status(tutor.getStatus())
                .id(tutor.getId())
                .bio(tutor.getBio())
                .experienceYears(tutor.getExperienceYears())
                .hourlyRate(tutor.getHourlyRate())
                .build()).toList();
    }

    @Override
    public UserResponse rejectedTutor(Long id) {
        Tutor tutor = tutorRepository.findByTutorUser(id).orElseThrow(()-> new AppException(ErrorCode.TUT_012));
        if(tutor.getUser().getRole() == (RoleType.TUTOR))
            throw new AppException(ErrorCode.TUT_014);
        tutor.getUser().setRole(RoleType.USER);
        tutor.setStatus(StatusType.REJECTED);
        tutorRepository.save(tutor);
        return UserResponse.builder()
                .roleType(tutor.getUser().getRole())
                .email(tutor.getUser().getEmail())
                .username(tutor.getUser().getUsername())
                .phone(tutor.getUser().getPhone())
                .userStatusType(tutor.getUser().getStatus())
                .build();
    }

    @Override
    public List<UserResponse> getAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user ->UserResponse.builder()
                                          .email(user.getEmail())
                                          .roleType(user.getRole())
                                          .phone(user.getPhone())
                                          .username(user.getUsername())
                                          .userStatusType(user.getStatus())
                                          .build())
                .toList();
    }

    @Override
    public UserResponse banned(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.USR_010));
        user.setStatus(UserStatusType.BANNED);
        userRepository.save(user);
        return UserResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .roleType(user.getRole())
                .userStatusType(user.getStatus())
                .build();
    }
}
