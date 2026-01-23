package com.tutorweb.api.repository;

import com.tutorweb.api.model.entity.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TutorRepository extends JpaRepository<Tutor,Long> {
    Optional<Tutor> findById(Long id);
}
