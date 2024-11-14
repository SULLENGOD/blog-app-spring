package com.auth.auth_test.application.dto.response;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class UserResponseDto {
    private UUID id;
    private String username;
    private String email;
    private Date createdAt;
    private Date updatedAt;
    private boolean enabled;
    private boolean credentialsNonExpired;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private List<String> authorities;
}
