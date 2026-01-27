package com.tutorweb.api.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name ="class")
@Getter
@Setter
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String description;
    private String subject;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private String address;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @OneToMany(mappedBy = "classes")
    private List<ApplyClass> applyClasses;






}
