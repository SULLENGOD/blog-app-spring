package com.posts.posts_service.adapter.in;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class UserContext {
    private String userId;
    private String username;
}
