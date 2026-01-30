package com.tutorweb.api.model.dto.response;


import com.tutorweb.api.type.StatusType;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class TutorResponse {
    private Long id;
    private String username;
    private String bio;
    private BigDecimal hourlyRate;
    private int experienceYears;
    private StatusType status;
}
