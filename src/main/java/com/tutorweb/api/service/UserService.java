package com.tutorweb.api.service;

import com.tutorweb.api.model.dto.request.TutorRequest;
import com.tutorweb.api.model.dto.request.UpdateUserRequest;
import com.tutorweb.api.model.dto.response.TutorResponse;
import com.tutorweb.api.model.dto.response.UserResponse;
import com.tutorweb.api.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


public interface UserService {
    UserResponse updateProfile(UpdateUserRequest updateUserRequest);
    UserResponse getMe();
    UserResponse approveAdmin();
    UserResponse approveManager();
    List<TutorResponse> getAllTutors();
    TutorResponse getTutor(Long id);
    UserResponse approveTutor(Long id);
}
