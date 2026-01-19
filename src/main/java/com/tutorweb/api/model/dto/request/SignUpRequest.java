package com.tutorweb.api.model.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignUpRequest {
    private String email;
    private String password;
    private String username;
    private String phone;

}
