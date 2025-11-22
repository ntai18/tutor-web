package org.taitai.tutor_backend.service;

import org.springframework.http.ResponseEntity;
import org.taitai.tutor_backend.request.TutorSignUpRequest;
import org.taitai.tutor_backend.response.TutorAssignmentResponse;

import java.util.List;


public interface TutorService {
    ResponseEntity<String> signUp(TutorSignUpRequest tutorRequest);
    List<TutorAssignmentResponse> getAssignedClasses();
}
