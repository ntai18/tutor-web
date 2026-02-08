package com.tutorweb.api.repository;

import com.tutorweb.api.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
    Optional<User> findById(Long id);
    @Query(value = "select c from User c join fetch c.tutor where c.id = :id")
    Optional<User> findByIdTutor(@Param("id") Long id);

    @Query(value = "SELECT u FROM User u ")
    List<User> findAllUser();

}
