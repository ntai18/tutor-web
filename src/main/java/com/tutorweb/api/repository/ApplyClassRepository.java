package com.tutorweb.api.repository;

import com.tutorweb.api.model.entity.ApplyClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplyClassRepository extends JpaRepository<ApplyClass, Long> {

    @Query(value = "SELECT a FROM ApplyClass a JOIN FETCH a.classes WHERE a.tutorId.id = :idTutor AND a.status = 'INVITED'")
    List<ApplyClass> findByStatus(@Param("idTutor") Long idTutor);

    @Query(value = "SELECT a FROM ApplyClass a JOIN a.tutorId t JOIN t.user u JOIN FETCH a.classes c WHERE u.username = :username AND a.status = 'INVITED'")
    List<ApplyClass> findByStatusMe(@Param("username") String username);

    @Query(value = "SELECT a FROM ApplyClass a JOIN a.tutorId t JOIN t.user u JOIN FETCH a.classes c WHERE u.username=:username AND a.id = :id ")
    Optional<ApplyClass> findByApplyClass(@Param("username") String username, @Param("id") Long idApplyClass);

}
