package org.taitai.tutor_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.taitai.tutor_backend.model.Classes;
import org.taitai.tutor_backend.request.ClassesRequest;
import org.taitai.tutor_backend.respone.ApplyTutorRespone;
import org.taitai.tutor_backend.service.ClassesService;

import java.util.List;

;

@RestController
@RequestMapping("/class")
@RequiredArgsConstructor
public class ClassroomUser {
    private final ClassesService classesService;

    @PostMapping
    @RequestMapping("/hiringstutor")
    public ResponseEntity<String> hiringstutor(@RequestBody ClassesRequest classesRequest) {
        return classesService.hiringsTutor(classesRequest);
    }

    @GetMapping
    public List<Classes> getClasses() {
        return classesService.getClasses();
    }

    @PostMapping("/{id}/apply")
    public ResponseEntity<ApplyTutorRespone> applyClasses(@PathVariable Long id) {
        return classesService.applyClass(id);
    }

}
