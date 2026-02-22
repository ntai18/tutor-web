package com.tutorweb.api.service;

import com.tutorweb.api.model.dto.request.SearchClassRequest;
import com.tutorweb.api.model.dto.request.TutorRequest;
import com.tutorweb.api.model.dto.response.ClassResponse;
import com.tutorweb.api.model.dto.response.TutorResponse;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface TutorService {
    TutorResponse getMe();
    TutorResponse updateProfile(TutorRequest tutorRequest);
    TutorResponse applyTutor(TutorRequest tutorRequest);
    List<ClassResponse> searchClass(SearchClassRequest searchClassRequest);

}
