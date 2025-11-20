package org.taitai.tutor_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.taitai.tutor_backend.model.Tutor;
import org.taitai.tutor_backend.model.User;

import java.util.Optional;

@Repository
public interface TutorRepo extends JpaRepository<Tutor, String> {
    Optional<Tutor> findTutorByEmail(String email);

    Optional<Tutor> findByUser(User user);
}
