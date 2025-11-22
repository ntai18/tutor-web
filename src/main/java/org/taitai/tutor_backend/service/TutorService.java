package org.taitai.tutor_backend.service;

import org.springframework.http.ResponseEntity;
import org.taitai.tutor_backend.model.Classes;
import org.taitai.tutor_backend.request.TutorSignUpRequest;

import java.util.List;


public interface TutorService {
    ResponseEntity<String> signUp(TutorSignUpRequest tutorRequest);
    List<Classes> getAssignedClasses();
}
