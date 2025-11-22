package org.taitai.tutor_backend.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.taitai.tutor_backend.model.Classes;
import org.taitai.tutor_backend.model.Tutor;
import org.taitai.tutor_backend.model.TutorApply;
import org.taitai.tutor_backend.model.User;
import org.taitai.tutor_backend.repository.TutorApplyRepo;
import org.taitai.tutor_backend.repository.TutorRepo;
import org.taitai.tutor_backend.repository.UserRepo;
import org.taitai.tutor_backend.request.TutorSignUpRequest;
import org.taitai.tutor_backend.service.TutorService;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TutorServiceImpl implements TutorService {
    private final TutorRepo tutorRepo;
    private final UserRepo userRepo;
    private final TutorApplyRepo tutorApplyRepo;

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
        tutor.setUser(user);
        tutor.setEmail(tutorRequest.getEmail());
        tutor.setSubject(tutorRequest.getSubject());
        tutorRepo.save(tutor);
        return ResponseEntity.ok().body(tutor.getEmail());
    }

    @Override
    public List<Classes> getAssignedClasses() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        if (user.getTutor() == null) {
            throw new RuntimeException("Tutor not found");
        }
        // vì tutor dùng id của user nên lấy id của user luôn
        log.info("Id user :{}" , user.getId());
        Tutor tutor = tutorRepo.findById(user.getTutor().getId()).orElseThrow(() -> new UsernameNotFoundException("Tutor not found"));
        List<TutorApply> tutorApply = tutorApplyRepo.findByTutor(tutor);
        if (tutorApply.isEmpty()) {
        throw new RuntimeException("Classes not found");
        }
        return tutorApply.stream()
                  .map(TutorApply::getClasses)
                  .toList();
    }
}
