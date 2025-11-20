package org.taitai.tutor_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.taitai.tutor_backend.type.ApplyStatus;

@Entity
public class TutorApply {

    @Id
    @GeneratedValue
    @Getter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    @Getter
    @Setter
    private Tutor tutor;

    @ManyToOne
    @JoinColumn(name = "class_id")
    @Getter
    @Setter
    private Classes classes;

    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private ApplyStatus status;
}