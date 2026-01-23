package com.tutorweb.api.service;

import com.tutorweb.api.model.dto.request.TutorRequest;
import com.tutorweb.api.model.dto.response.TutorResponse;

public interface TutorService {
    TutorResponse appllyTutor(TutorRequest tutorRequest);
}
