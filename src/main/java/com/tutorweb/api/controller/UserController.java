package com.tutorweb.api.controller;

import com.tutorweb.api.model.dto.request.UpdateUserRequest;
import com.tutorweb.api.model.dto.response.ApiResponse;
import com.tutorweb.api.model.dto.response.TutorResponse;
import com.tutorweb.api.model.dto.response.UserResponse;
import com.tutorweb.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PatchMapping("/update")
    public ApiResponse<UserResponse> updateProfile(@RequestBody UpdateUserRequest updateUserRequest) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(userService.updateProfile(updateUserRequest));
        return apiResponse;
    }

    @GetMapping("/me")
    public ApiResponse<UserResponse> getMe() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(userService.getMe());
        return apiResponse;
    }

    @PatchMapping("/approve-admin")
    public ApiResponse<UserResponse> approveAdmin(){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(userService.approveAdmin());
        return apiResponse;
    }

    @PatchMapping("/approve-manager")
    public ApiResponse<UserResponse> approveManager(){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(userService.approveManager());
        return apiResponse;
    }

    @PatchMapping("/approve-tutor/{id}")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ApiResponse<UserResponse> approveTutor(@PathVariable Long id){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(userService.approveTutor(id));
        return apiResponse;
    }

    @GetMapping("/list-tutor")
    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    public ApiResponse<List<TutorResponse>> getAllTutors() {
        ApiResponse<List<TutorResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setData(userService.getAllTutors());
        return apiResponse;
    }
    @GetMapping("/tutor/{id}")
    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    public ApiResponse<TutorResponse> getTutors(@PathVariable Long id) {
        ApiResponse<TutorResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(userService.getTutor(id));
        return apiResponse;
    }
}
