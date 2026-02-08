package com.tutorweb.api.service;

import com.tutorweb.api.model.dto.request.UpdateUserRequest;
import com.tutorweb.api.model.dto.response.TutorResponse;
import com.tutorweb.api.model.dto.response.UserResponse;

import java.util.List;


public interface UserService {
    UserResponse updateProfile(UpdateUserRequest updateUserRequest);
    UserResponse getMe();
    UserResponse approveAdmin();
    UserResponse approveManager();
    List<TutorResponse> getAllTutors();
    TutorResponse getTutor(Long id);
    UserResponse approveTutor(Long id);
    List<TutorResponse> getTutorPending();
    UserResponse rejectedTutor(Long id);
    List<UserResponse> getAllUser();
    UserResponse banned(Long id);
}
