package com.tutorweb.api.service.impl;

import com.tutorweb.api.exception.AppException;
import com.tutorweb.api.exception.ErrorCode;
import com.tutorweb.api.model.dto.request.ClassRequest;
import com.tutorweb.api.model.dto.response.ClassResponse;
import com.tutorweb.api.model.entity.Class;
import com.tutorweb.api.model.entity.User;
import com.tutorweb.api.repository.ClassRepository;
import com.tutorweb.api.repository.UserRepository;
import com.tutorweb.api.service.ClassService;
import com.tutorweb.api.type.StatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {
    private final UserRepository userRepository;
    private final ClassRepository classRepository;

    @Override
    public ClassResponse createClass(ClassRequest classRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(()-> new AppException(ErrorCode.USR_010));
        Class newClass = new Class();
        newClass.setTitle(classRequest.getTitle());
        newClass.setAddress(classRequest.getAddress());
        newClass.setDescription(classRequest.getDescription());
        newClass.setPrice(classRequest.getPrice());
        newClass.setSubject(classRequest.getSubject());
        newClass.setUserId(user);
        newClass.setStatus(StatusType.PENDING);
        classRepository.save(newClass);
        return ClassResponse.builder()
                .title(classRequest.getTitle())
                .description(classRequest.getDescription())
                .price(classRequest.getPrice())
                .subject(classRequest.getSubject())
                .address(classRequest.getAddress())
                .build();
    }

    @Override
    public ClassResponse editClass(Long classId, ClassRequest classRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user =  userRepository.findByUsername(username).orElseThrow(()-> new AppException(ErrorCode.USR_010));
        Class classes = classRepository.findOwnedClass(user.getId(), user).orElseThrow(()-> new AppException(ErrorCode.CL_018));
        if((classRequest.getTitle() != null))
            classes.setTitle(classRequest.getTitle());
        if((classRequest.getDescription() != null))
            classes.setDescription(classRequest.getDescription());
        if((classRequest.getPrice() != null))
            classes.setPrice(classRequest.getPrice());
        if((classRequest.getSubject() != null))
            classes.setSubject(classRequest.getSubject());
        if((classRequest.getAddress() != null))
            classes.setAddress(classRequest.getAddress());
        classRepository.save(classes);
        return ClassResponse.builder()
                .address(classes.getAddress())
                .description(classes.getDescription())
                .price(classes.getPrice())
                .subject(classes.getSubject())
                .title(classes.getTitle())
                .build();
    }

}
