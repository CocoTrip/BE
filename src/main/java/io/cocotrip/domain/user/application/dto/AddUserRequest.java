package io.cocotrip.domain.user.application.dto;

import lombok.Getter;

@Getter
public class AddUserRequest {
    private String email;
    private String password;
}
