package org.taitai.tutor_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.taitai.tutor_backend.request.TutorSignUpRequest;
import org.taitai.tutor_backend.response.TutorAssignmentResponse;
import org.taitai.tutor_backend.service.TutorService;

import java.util.List;


@RestController
@RequestMapping("/tutor")
@RequiredArgsConstructor
public class TutorController {
    private final TutorService tutorService;
    @PostMapping("/apply")
    public ResponseEntity<String> signup(@RequestBody TutorSignUpRequest tutorSignUpRequest) {
        return tutorService.signUp(tutorSignUpRequest);
    }
    @GetMapping("/assignments")
    public List<TutorAssignmentResponse> getAssignments() {
        return tutorService.getAssignedClasses();
    }
}
