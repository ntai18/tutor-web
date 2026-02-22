package com.tutorweb.api.service;

import com.tutorweb.api.model.dto.request.ClassRequest;
import com.tutorweb.api.model.dto.response.ApiResponse;
import com.tutorweb.api.model.dto.response.ApplyClassResponse;
import com.tutorweb.api.model.dto.response.ClassResponse;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ClassService {
    ClassResponse createClass(ClassRequest classRequest);
    ClassResponse editClassMe(Long classId, ClassRequest classRequest);
    Object deleteClassMe(Long classId);
    List<ClassResponse> getClassMe();
    Object deleteClassUser(Long classId);
    ClassResponse editClassUser(Long classId, ClassRequest classRequest);
    ApplyClassResponse invitedTutor(Long idTutor, Long idClass);
    List<ApplyClassResponse> getClassInvitedTutor(Long idTutor);
    List<ApplyClassResponse> getClassInvitedMe();
    ApplyClassResponse tutorAccept(Long idApplyClass);
    ApplyClassResponse tutorReject(Long idApplyClass);
    List<ClassResponse> getAllClass();

}
