package com.tutorweb.api.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignUpResponse {
    private String email;
    private String phone;
    private String username;
}
