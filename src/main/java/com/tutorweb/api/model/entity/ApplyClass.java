package com.tutorweb.api.model.entity;

import com.tutorweb.api.type.StatusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "apply_class",
       uniqueConstraints = {
            @UniqueConstraint(columnNames = {"tutor_id", "class_id"})//cái này check trùng
       }
    )
@Getter
@Setter
public class ApplyClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusType status;

    @ManyToOne
    @JoinColumn(name = "tutor_id",  nullable = false)
    private Tutor tutorId;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private RoomClass classes;

}
