package org.taitai.tutor_backend.service;

import org.springframework.http.ResponseEntity;
import org.taitai.tutor_backend.model.Classes;
import org.taitai.tutor_backend.request.ClassesRequest;
import org.taitai.tutor_backend.respone.ApplyTutorRespone;

import java.util.List;


public interface ClassesService {
    ResponseEntity<String> hiringsTutor(ClassesRequest classesRequest);

    List<Classes> getClasses();

    ResponseEntity<ApplyTutorRespone> applyClass(Long classId);
}
