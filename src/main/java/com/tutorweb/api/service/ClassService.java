package com.tutorweb.api.service;

import com.tutorweb.api.model.dto.request.ClassRequest;
import com.tutorweb.api.model.dto.response.ClassResponse;

import java.util.List;

public interface ClassService {
    ClassResponse createClass(ClassRequest classRequest);
    ClassResponse editClass(Long classId, ClassRequest classRequest);
    void deleteClassMe(Long classId);
    List<ClassResponse> getClassMe();
}
