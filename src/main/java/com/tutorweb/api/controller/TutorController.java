package com.tutorweb.api.controller;

import com.tutorweb.api.model.dto.request.SearchClassRequest;
import com.tutorweb.api.model.dto.request.TutorRequest;
import com.tutorweb.api.model.dto.response.ApiResponse;
import com.tutorweb.api.model.dto.response.ClassResponse;
import com.tutorweb.api.model.dto.response.TutorResponse;
import com.tutorweb.api.service.TutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tutor")
@RequiredArgsConstructor
public class TutorController {
    private final TutorService tutorService;

    @GetMapping("/me")
    public ApiResponse<TutorResponse> getMe() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(tutorService.getMe());
        return apiResponse;
    }

    @PatchMapping("/update")
    public ApiResponse<TutorResponse> updateProfile(@RequestBody TutorRequest tutorRequest) {
        ApiResponse<TutorResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(tutorService.updateProfile(tutorRequest));
        return apiResponse;
    }

    @PostMapping("/apply")
    public ApiResponse<TutorResponse> applyTutor(@RequestBody TutorRequest tutorRequest) {
        ApiResponse<TutorResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(tutorService.applyTutor(tutorRequest));
        return apiResponse;
    }

    @PostMapping("/class/search")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','TUTOR')")
    public ApiResponse<List<ClassResponse>> searchClass(@RequestBody SearchClassRequest searchClassRequest) {
        ApiResponse<List<ClassResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setData(tutorService.searchClass(searchClassRequest));
        return apiResponse;
    }

}
