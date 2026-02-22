package com.tutorweb.api.repository;

import com.tutorweb.api.model.entity.RoomClass;
import com.tutorweb.api.model.entity.User;
import com.tutorweb.api.type.StatusType;
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

    @Query(value = " SELECT r FROM RoomClass r WHERE (:title IS NULL OR LOWER(r.address) LIKE LOWER(CONCAT('%', :title,'%'))) " +
            "AND (:description IS NULL OR LOWER(r.description) LIKE LOWER(CONCAT('%', :description,'%'))) " +
            "AND (:subject IS NULL OR LOWER(r.subject) LIKE LOWER(CONCAT('%', :subject, '%'))) " +
            "AND (:priceMin IS NULL OR r.price >= :priceMin) " +
            "AND (:priceMax IS NULL OR r.price <= :priceMax) " +
            "AND (:address IS NULL OR LOWER(r.address) LIKE LOWER(CONCAT('%', :address, '%')))" +
            "AND (:status IS NULL OR r.status = :status) ")

    List<RoomClass> searchClass(@Param("title") String title,
                                @Param("description") String description,
                                @Param("subject") String subject,
                                @Param("priceMin") Double priceMin,
                                @Param("priceMax") Double priceMax,
                                @Param("address") String address,
                                @Param("status") StatusType status);

}
