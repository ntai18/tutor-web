package com.tutorweb.api.controller;

import com.tutorweb.api.model.dto.request.TutorRequest;
import com.tutorweb.api.model.dto.response.ApiResponse;
import com.tutorweb.api.model.dto.response.TutorResponse;
import com.tutorweb.api.service.TutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tutor")
@RequiredArgsConstructor
public class TutorController {
    private final TutorService tutorService;

    @PostMapping("/apply")
    public ApiResponse<TutorResponse> applyTutor(@RequestBody TutorRequest tutorRequest) {
        ApiResponse<TutorResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(tutorService.applyTutor(tutorRequest));
        return apiResponse;
    }
    @PatchMapping("/update")
    public ApiResponse<TutorResponse> updateProfile(@RequestBody TutorRequest tutorRequest) {
        ApiResponse<TutorResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(tutorService.updateProfile(tutorRequest));
        return apiResponse;
    }
}
