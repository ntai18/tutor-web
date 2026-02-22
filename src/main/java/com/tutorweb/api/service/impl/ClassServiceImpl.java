package com.tutorweb.api.service.impl;

import com.tutorweb.api.exception.AppException;
import com.tutorweb.api.exception.ErrorCode;
import com.tutorweb.api.model.dto.request.ClassRequest;
import com.tutorweb.api.model.dto.response.ApplyClassResponse;
import com.tutorweb.api.model.dto.response.ClassResponse;
import com.tutorweb.api.model.entity.ApplyClass;
import com.tutorweb.api.model.entity.RoomClass;
import com.tutorweb.api.model.entity.Tutor;
import com.tutorweb.api.model.entity.User;
import com.tutorweb.api.repository.ApplyClassRepository;
import com.tutorweb.api.repository.ClassRepository;
import com.tutorweb.api.repository.TutorRepository;
import com.tutorweb.api.repository.UserRepository;
import com.tutorweb.api.service.ClassService;
import com.tutorweb.api.type.StatusType;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService {
    private final UserRepository userRepository;
    private final ClassRepository classRepository;
    private final TutorRepository tutorRepository;
    private final EntityManager entityManager;
    private final ApplyClassRepository applyClassRepository;

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

    @Override
    public ApplyClassResponse invitedTutor(Long idTutor, Long idClass) {
        Tutor tutorRef = entityManager.getReference(Tutor.class, idTutor);
        RoomClass roomClassRef = entityManager.getReference(RoomClass.class, idClass);
        ApplyClass applyClass = new ApplyClass();
        applyClass.setStatus(StatusType.INVITED);
        applyClass.setTutorId(tutorRef);
        applyClass.setClasses(roomClassRef);
        applyClassRepository.save(applyClass);
        return ApplyClassResponse.builder()
                .idTutor(idTutor)
                .classId(idClass)
                .status(applyClass.getStatus())
                .build();

    }

    @Override
    public List<ApplyClassResponse> getClassInvitedTutor(Long idTutor) {
        List<ApplyClass> applyClasses = applyClassRepository.findByStatus(idTutor);

        return applyClasses.stream().map(applyClass -> ApplyClassResponse.builder()
                                                                                    .classId(applyClass.getClasses().getId())
                                                                                    .status(applyClass.getStatus())
                                                                                    .title(applyClass.getClasses().getTitle())
                                                                                    .subject(applyClass.getClasses().getSubject())
                                                                                    .price(applyClass.getClasses().getPrice())
                                                                                    .build())
                .toList();
    }

    @Override
    public List<ApplyClassResponse> getClassInvitedMe() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<ApplyClass> applyClasses = applyClassRepository.findByStatusMe(username);
        return applyClasses.stream().map(applyClass -> ApplyClassResponse.builder()
                                                                                    .classId(applyClass.getClasses().getId())
                                                                                    .status(applyClass.getStatus())
                                                                                    .title(applyClass.getClasses().getTitle())
                                                                                    .subject(applyClass.getClasses().getSubject())
                                                                                    .price(applyClass.getClasses().getPrice())
                                                                                    .build())
                .toList();
    }

    @Override
    public ApplyClassResponse tutorAccept(Long idApplyClass) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        ApplyClass applyClass =
                applyClassRepository.findByApplyClass(username, idApplyClass).orElseThrow(()-> new AppException(ErrorCode.BK_016));
        if(applyClass.getStatus() == StatusType.ACCEPTED)
            throw new AppException(ErrorCode.BK_018);
        applyClass.setStatus(StatusType.ACCEPTED);
        applyClassRepository.save(applyClass);
        return ApplyClassResponse.builder()
                .status(applyClass.getStatus())
                .classId(applyClass.getClasses().getId())
                .subject(applyClass.getClasses().getSubject())
                .build();
    }

    @Override
    public ApplyClassResponse tutorReject(Long idApplyClass) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        ApplyClass applyClass =
                applyClassRepository.findByApplyClass(username, idApplyClass).orElseThrow(()-> new AppException(ErrorCode.BK_016));
        if(applyClass.getStatus() == StatusType.REJECTED)
            throw new AppException(ErrorCode.BK_019);
        applyClass.setStatus(StatusType.REJECTED);
        applyClassRepository.save(applyClass);
        return ApplyClassResponse.builder()
                .status(applyClass.getStatus())
                .classId(applyClass.getClasses().getId())
                .subject(applyClass.getClasses().getSubject())
                .build();
    }

    @Override
    public List<ClassResponse> getAllClass() {
        List<RoomClass> classes = classRepository.findAll();
        return classes.stream().map(cl -> ClassResponse.builder()
                                                                    .title(cl.getTitle())
                                                                    .description(cl.getDescription())
                                                                    .price(cl.getPrice())
                                                                    .subject(cl.getSubject())
                                                                    .address(cl.getAddress())
                                                                    .build())
                .toList();
    }

}
