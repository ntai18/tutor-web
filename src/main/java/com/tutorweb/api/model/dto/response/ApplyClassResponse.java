package com.tutorweb.api.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tutorweb.api.type.StatusType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplyClassResponse {
    private StatusType status;
    private Long classId;
    private Long idTutor;
    private String title;
    private String subject;
    private Double price;

}
