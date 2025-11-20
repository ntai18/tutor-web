package org.taitai.tutor_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tutor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tutor {
   @Id
    @Column(name = "id")
    private Long id;


    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "email", columnDefinition = "VARCHAR(100)")
    private String email;

    @Column(name = "subject", columnDefinition = "VARCHAR(100)")
    private String subject;
}

