package com.tutorweb.api.repository;

import com.tutorweb.api.model.entity.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository<Tutor,Long> {
    @Query(value = "SELECT t FROM Tutor t WHERE t.id = :id")
    Optional<Tutor> findById(@Param("id") Long id);

    @Query(value = "SELECT t FROM Tutor t JOIN t.user u WHERE u.username = :username ")
    Optional<Tutor> findByMe(@Param("username") String username);

    @Query(value = "SELECT t FROM Tutor t JOIN FETCH t.user u WHERE u.id = :id ")
    Optional<Tutor> findByTutorUser(@Param("id") Long id);

    //hard-code
    @Query(value = "SELECT t FROM Tutor t WHERE t.status = 'PENDING'")
    List<Tutor> findTutorPending();

    //hard-code
    @Query(value = "SELECT t FROM Tutor t JOIN FETCH t.user WHERE t.status = 'APPROVED' ")
    List<Tutor> findTutorApproved();
}
