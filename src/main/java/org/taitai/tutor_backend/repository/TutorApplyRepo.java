package org.taitai.tutor_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.taitai.tutor_backend.model.Classes;
import org.taitai.tutor_backend.model.Tutor;
import org.taitai.tutor_backend.model.TutorApply;

import java.util.List;
import java.util.Optional;

@Repository
public interface TutorApplyRepo extends JpaRepository<TutorApply, Long> {
    Optional<TutorApply> findByTutorAndClasses(Tutor tutor, Classes classes);
    List<TutorApply> findByTutor(Tutor tutor);
}
