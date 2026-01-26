package com.tutorweb.api.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassRequest {
    private String title;
    private String description;
    private String subject;
    private Double price;
    private String address;
}
