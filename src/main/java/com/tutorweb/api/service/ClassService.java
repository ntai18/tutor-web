package com.tutorweb.api.service;

import com.tutorweb.api.model.dto.request.ClassRequest;
import com.tutorweb.api.model.dto.response.ClassResponse;

import java.util.List;

public interface ClassService {
    ClassResponse createClass(ClassRequest classRequest);
    ClassResponse editClassMe(Long classId, ClassRequest classRequest);
    Object deleteClassMe(Long classId);
    List<ClassResponse> getClassMe();
    Object deleteClassUser(Long classId);
    ClassResponse editClassUser(Long classId, ClassRequest classRequest);
}
