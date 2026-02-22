package com.tutorweb.api.model.dto.request;

import com.tutorweb.api.type.StatusType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchClassRequest {
    private String title;
    private String description;
    private String subject;
    private Double priceMin;
    private Double priceMax;
    private String address;
    private StatusType status;
}
