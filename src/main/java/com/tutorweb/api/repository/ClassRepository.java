package com.tutorweb.api.repository;

import com.tutorweb.api.model.entity.Class;
import com.tutorweb.api.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {

    @Query(value = " SELECT c FROM Class c WHERE c.id = :classId AND c.userId = :userId")
    Optional<Class> findOwnedClass(@Param("classId") Long classId, @Param("userId") User userId);

}
