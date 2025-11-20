package org.taitai.tutor_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.taitai.tutor_backend.model.TutorApply;

@Repository
public interface TutorApplyRepo extends JpaRepository<TutorApply, Long> {

}
