package com.auth.auth_test.application.dto.request;

import lombok.Data;

@Data
public class LoginUserDto {
    private String username;
    private String password;
}
