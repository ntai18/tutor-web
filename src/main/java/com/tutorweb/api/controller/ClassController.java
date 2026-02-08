package com.tutorweb.api.controller;

import com.tutorweb.api.model.dto.request.ClassRequest;
import com.tutorweb.api.model.dto.response.ApiResponse;
import com.tutorweb.api.model.dto.response.ClassResponse;
import com.tutorweb.api.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/me")
    public ApiResponse<List<ClassResponse>> getClassMe() {
        ApiResponse<List<ClassResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setData(classService.getClassMe());
        return apiResponse;
    }

    @PatchMapping("/edit/{id}")
    public ApiResponse<ClassResponse> editClassMe(@PathVariable("id") Long idClass, @RequestBody ClassRequest classRequest) {
        ApiResponse<ClassResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(classService.editClassMe(idClass, classRequest));
        return apiResponse ;
    }
    @DeleteMapping ("/delete/{id}")
    public ApiResponse deleteClassMe(@PathVariable Long id) {
        ApiResponse apiResponse = new ApiResponse<>();

        apiResponse.setData(classService.deleteClassMe(id));
        return apiResponse;
    }
    @DeleteMapping ("/admin/delete/{id-class}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ApiResponse deleteClassUser(@PathVariable("id-class") Long idClass ) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setData(classService.deleteClassUser(idClass));
        return apiResponse;
    }

    @PatchMapping("/admin/edit/{id-class}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ApiResponse<ClassResponse> editClassUser(@PathVariable("id-class") Long idClass, @RequestBody ClassRequest classRequest) {
        ApiResponse<ClassResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(classService.editClassUser(idClass, classRequest));
        return apiResponse ;
    }
}
