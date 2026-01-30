package com.tutorweb.api.service.impl;

import com.tutorweb.api.exception.AppException;
import com.tutorweb.api.exception.ErrorCode;
import com.tutorweb.api.model.dto.request.TutorRequest;
import com.tutorweb.api.model.dto.response.TutorResponse;
import com.tutorweb.api.model.entity.Tutor;
import com.tutorweb.api.model.entity.User;
import com.tutorweb.api.repository.TutorRepository;
import com.tutorweb.api.repository.UserRepository;
import com.tutorweb.api.service.TutorService;
import com.tutorweb.api.type.RoleType;
import com.tutorweb.api.type.StatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TutorServiceImpl implements TutorService {
    private final UserRepository userRepository;
    private final TutorRepository tutorRepository;

    @Override
    public TutorResponse applyTutor(TutorRequest tutorRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(()-> new AppException(ErrorCode.USR_010));
        if(user.getRole().equals(RoleType.TUTOR))
            throw new AppException(ErrorCode.TUT_014);
        Tutor tutor = new Tutor();
        tutor.setUser(user);
        tutor.setBio(tutorRequest.getBio());
        tutor.setHourlyRate(tutorRequest.getHourlyRate());
        tutor.setExperienceYears(tutorRequest.getExperienceYears());
        tutor.setIdentityCardUrl(tutorRequest.getIdentityCardUrl());
        tutor.setStatus(StatusType.PENDING);
        user.setTutor(tutor);
        userRepository.save(user);
        return TutorResponse.builder()
                .id(user.getId())
                .username(username)
                .bio(tutorRequest.getBio())
                .hourlyRate(tutorRequest.getHourlyRate())
                .experienceYears(tutorRequest.getExperienceYears())
                .status(tutor.getStatus())
                .build();
    }

    @Override
    public TutorResponse updateProfile(TutorRequest tutorRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(()-> new AppException(ErrorCode.USR_010));
        if(!(user.getRole().equals(RoleType.TUTOR)))
            throw new AppException(ErrorCode.TUT_013);
        Tutor tutor =
                tutorRepository.findById(Long.valueOf(user.getId())).orElseThrow(()-> new AppException(ErrorCode.TUT_012));
        if(tutorRequest.getBio() != null)
            tutor.setBio(tutorRequest.getBio());
        if (tutorRequest.getHourlyRate() != null)
            tutor.setHourlyRate(tutorRequest.getHourlyRate());
        if(tutorRequest.getExperienceYears() != null)
            tutor.setExperienceYears(tutorRequest.getExperienceYears());
        tutorRepository.save(tutor);
        return TutorResponse.builder()
                .username(username)
                .id(tutor.getId())
                .bio(tutor.getBio())
                .hourlyRate(tutor.getHourlyRate())
                .experienceYears(tutor.getExperienceYears())
                .build();
    }
}
