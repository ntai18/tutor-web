package org.taitai.tutor_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.taitai.tutor_backend.model.Classes;

import java.util.List;


@Repository
public interface ClassesRepo extends JpaRepository<Classes, Long> {
    List<Classes> findByStatus(String status);
}
