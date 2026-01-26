package com.tutorweb.api.model.dto.response;

import com.tutorweb.api.type.ApplyStatusType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TutorResponse {
    private Long id;
    private String username;
}
