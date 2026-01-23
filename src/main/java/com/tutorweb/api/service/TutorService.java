package com.tutorweb.api.service;

import com.tutorweb.api.model.dto.request.TutorRequest;
import com.tutorweb.api.model.dto.response.TutorResponse;

public interface TutorService {
    TutorResponse applyTutor(TutorRequest tutorRequest);
    TutorResponse updateProfile(TutorRequest tutorRequest);
}
