package com.tutorweb.api.controller;

import com.tutorweb.api.model.dto.request.ClassRequest;
import com.tutorweb.api.model.dto.response.ApiResponse;
import com.tutorweb.api.model.dto.response.ApplyClassResponse;
import com.tutorweb.api.model.dto.response.ClassResponse;
import com.tutorweb.api.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/class")
@RequiredArgsConstructor
public class ClassController {
    private  final ClassService classService;


    @PostMapping("/manager/invited/{idTutor}/{idClass}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ApiResponse<ApplyClassResponse> invitedTutor(@PathVariable Long idTutor, @PathVariable Long idClass){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(classService.invitedTutor(idTutor, idClass));
        return apiResponse;
    }

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
        return apiResponse;
    }
    @GetMapping("/manager/getClassInvitedTutor/{idTutor}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ApiResponse<List<ApplyClassResponse>> getClassInvitedTutor(@PathVariable("idTutor") Long idTutor) {
        ApiResponse<List<ApplyClassResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setData(classService.getClassInvitedTutor(idTutor));
        return apiResponse;
    }
    @GetMapping("/tutor/getClassInvitedMe")
    public ApiResponse<List<ApplyClassResponse>> getClassInvitedMe() {
        ApiResponse<List<ApplyClassResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setData(classService.getClassInvitedMe());
        return apiResponse;
    }
    @PatchMapping("/tutor/{id}/accept")
    @PreAuthorize("hasRole('TUTOR')")
    public ApiResponse<ApplyClassResponse> tutorAccept(@PathVariable("id") Long idApplyClass) {
        ApiResponse<ApplyClassResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(classService.tutorAccept(idApplyClass));
        return apiResponse;
    }

    @PatchMapping("/tutor/{id}/reject")
    @PreAuthorize("hasRole('TUTOR')")
    public ApiResponse<ApplyClassResponse> tutorReject(@PathVariable("id") Long idApplyClass) {
        ApiResponse<ApplyClassResponse> apiResponse = new ApiResponse<>();
        apiResponse.setData(classService.tutorReject(idApplyClass));
        return apiResponse;
    }

    @GetMapping("/getAll")
    public ApiResponse<List<ClassResponse>> getAllClass() {
        ApiResponse<List<ClassResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setData(classService.getAllClass());
        return apiResponse;
    }

}
