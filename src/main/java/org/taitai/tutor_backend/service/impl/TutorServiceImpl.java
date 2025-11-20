package org.taitai.tutor_backend.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.taitai.tutor_backend.model.Tutor;
import org.taitai.tutor_backend.model.User;
import org.taitai.tutor_backend.repository.TutorRepo;
import org.taitai.tutor_backend.repository.UserRepo;
import org.taitai.tutor_backend.request.TutorSignUpRequest;
import org.taitai.tutor_backend.service.TutorService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TutorServiceImpl implements TutorService {
    private final TutorRepo tutorRepo;
    private final UserRepo userRepo;

    @Override
    public ResponseEntity<String> signUp(TutorSignUpRequest tutorRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepo.findByUsername(username)
                            .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        if (user.getTutor() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(Map.of("message", "Bạn đã đăng ký làm gia sư rồi!").toString());
        }
        if (tutorRepo.findTutorByEmail(tutorRequest.getEmail()).isPresent())
            throw new RuntimeException("Email already exists");
        Tutor tutor = new Tutor();
        tutor.setEmail(tutorRequest.getEmail());
        tutor.setSubject(tutorRequest.getSubject());
        tutor.setUser(user);
        tutorRepo.save(tutor);
        return ResponseEntity.ok().body(tutor.getEmail());
    }
}
