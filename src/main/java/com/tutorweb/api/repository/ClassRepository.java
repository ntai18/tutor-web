package com.tutorweb.api.repository;

import com.tutorweb.api.model.entity.RoomClass;
import com.tutorweb.api.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRepository extends JpaRepository<RoomClass, Long> {

    @Query(value = " SELECT c FROM RoomClass c WHERE c.id = :classId AND c.userId = :userId")
    Optional<RoomClass> findOwnedClass(@Param("classId") Long classId, @Param("userId") User userId);

    @Query(value = "SELECT c FROM RoomClass c WHERE c.userId = :userId")
    List<RoomClass> findClassMe(@Param("userId") User user);

    @Query(value = "SELECT c FROM RoomClass c WHERE c.id = :classId")
    Optional<RoomClass> findClassUser(@Param("classId") Long classId);

    @Query(value = "SELECT c FROM RoomClass c WHERE c.id = :idClass")
    Optional<RoomClass> findByClass(@Param("idClass") Long idClass);

}
