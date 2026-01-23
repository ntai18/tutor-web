package com.tutorweb.api.model.dto.request;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class TutorRequest {
    private String bio;
    private BigDecimal hourlyRate;
    private int experienceYears ;
    private String identityCardUrl;


}
