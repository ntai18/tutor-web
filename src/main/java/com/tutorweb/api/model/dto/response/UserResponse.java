package com.tutorweb.api.model.dto.response;

import com.tutorweb.api.type.RoleType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse {
    private String email;
    private String phone;
    private String username;
    private RoleType roleType;
}
