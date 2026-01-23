package com.tutorweb.api.model.entity;

import com.tutorweb.api.type.TutorStatusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Table(name ="tutor")
@Getter
@Setter
public class Tutor {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id" , nullable = false ,  unique = true,  referencedColumnName = "id" )
    //referencedColumnName = "id" : lưu ý chỗ này phải chỉ định
    private User user;

    @Column(nullable = false , length = 15 , columnDefinition = "TEXT")
    private String bio;

    @Column(name = "hourly_rate", precision = 10 , scale = 2)
    private BigDecimal hourlyRate;

    @Column(name = "experience_years")
    private int experienceYears ;

    @Column(name ="identity_car_url" , nullable = false , length = 500 , unique = true)
    private String identityCardUrl ;

    @Column(name ="profile_status")
    @Enumerated(EnumType.STRING)
    private TutorStatusType profileStatus;

}
