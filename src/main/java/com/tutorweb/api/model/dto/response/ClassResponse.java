package com.tutorweb.api.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClassResponse {
    private String title;
    private String description;
    private String subject;
    private Double price;
    private String address;
}
