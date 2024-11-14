package com.posts.posts_service.application.dto.request;

import lombok.Data;

@Data
public class RequestPostDto {
    private String title;
    private String content;
    private String excerpt;
}
