package com.tutorweb.api.controller;

import com.tutorweb.api.model.dto.request.UpdateUserRequest;
import com.tutorweb.api.model.dto.response.ApiResponse;
import com.tutorweb.api.model.dto.response.UserResponse;
import com.tutorweb.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PatchMapping("/update")
    public ApiResponse<UserResponse> updateProfile( @RequestBody UpdateUserRequest updateUserRequest) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(userService.updateProfile(updateUserRequest));
        return apiResponse;
    }
}
