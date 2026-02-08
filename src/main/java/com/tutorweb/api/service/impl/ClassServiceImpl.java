package com.tutorweb.api.service.impl;

import com.tutorweb.api.exception.AppException;
import com.tutorweb.api.exception.ErrorCode;
import com.tutorweb.api.model.dto.request.ClassRequest;
import com.tutorweb.api.model.dto.response.ClassResponse;
import com.tutorweb.api.model.entity.RoomClass;
import com.tutorweb.api.model.entity.User;
import com.tutorweb.api.repository.ClassRepository;
import com.tutorweb.api.repository.UserRepository;
import com.tutorweb.api.service.ClassService;
import com.tutorweb.api.type.StatusType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {
    private final UserRepository userRepository;
    private final ClassRepository classRepository;

    @Override
    public ClassResponse createClass(ClassRequest classRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(()-> new AppException(ErrorCode.USR_010));
        RoomClass newClass = new RoomClass();
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
    public ClassResponse editClassMe(Long classId, ClassRequest classRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user =  userRepository.findByUsername(username).orElseThrow(()-> new AppException(ErrorCode.USR_010));
        RoomClass classes = classRepository.findOwnedClass(user.getId(), user).orElseThrow(()-> new AppException(ErrorCode.CL_018));
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

    @Override
    public Object deleteClassMe(Long classId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user =  userRepository.findByUsername(username).orElseThrow(()-> new AppException(ErrorCode.USR_010));
        RoomClass classes =
                classRepository.findOwnedClass(classId, user).orElseThrow(()-> new AppException(ErrorCode.CL_018));
        classRepository.delete(classes);
        return null;
    }

    @Override
    public List<ClassResponse> getClassMe() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(()-> new AppException(ErrorCode.USR_010));
        List<RoomClass> cls = classRepository.findClassMe(user);
        if(cls.isEmpty())
            throw new AppException(ErrorCode.CL_019);
        return cls.stream().map(classes -> ClassResponse.builder()
                                                               .title(classes.getTitle())
                                                               .subject(classes.getSubject())
                                                               .price(classes.getPrice())
                                                               .address(classes.getAddress())
                                                               .description(classes.getDescription())
                                                               .build())
                .toList();
    }

    @Override
    public Object deleteClassUser(Long classId) {
        RoomClass cls = classRepository.findClassUser(classId).orElseThrow(()-> new AppException(ErrorCode.CL_019));
        classRepository.delete(cls);
        return null;
    }

    @Override
    public ClassResponse editClassUser(Long classId, ClassRequest classRequest) {
        RoomClass classes = classRepository.findByClass(classId).orElseThrow(()-> new AppException(ErrorCode.CL_018));
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
