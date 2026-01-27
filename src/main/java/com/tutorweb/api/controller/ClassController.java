package com.tutorweb.api.controller;

import com.tutorweb.api.model.dto.request.ClassRequest;
import com.tutorweb.api.model.dto.response.ApiResponse;
import com.tutorweb.api.model.dto.response.ClassResponse;
import com.tutorweb.api.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/class")
@RequiredArgsConstructor
public class ClassController {
    private  final ClassService classService;

    @PostMapping("/create")
    public ApiResponse<ClassResponse> createClass(@RequestBody ClassRequest classRequest) {
        ApiResponse<ClassResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(classService.createClass(classRequest));
        return apiResponse;
    }

    @PatchMapping("/edit/{id}")
    public ApiResponse<ClassResponse> editClass(@PathVariable Long id, @RequestBody ClassRequest classRequest) {
        ApiResponse<ClassResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(classService.editClass(id, classRequest));
        return apiResponse ;
    }
}
