package com.auth.auth_test.application.dto.request;

import lombok.Data;

@Data
public class CreateUserDto {
    private String email;
    private String password;
    private String username;
}
