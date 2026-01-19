package com.tutorweb.api.model.entity;

import com.tutorweb.api.type.RoleType;
import com.tutorweb.api.type.UserStatusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "user_app")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true , length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "username", nullable = false , length = 100)
    private String username;

    @Column(nullable = false , unique = true , length = 15)
    private String phone;

    @Column(nullable = false , length = 15)
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Column(nullable = false , length = 20)
    @Enumerated(EnumType.STRING)
    private UserStatusType status;

    @Column(name = "create_at", nullable = false )
    private LocalDateTime createdAt ;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Tutor tutor;

}
