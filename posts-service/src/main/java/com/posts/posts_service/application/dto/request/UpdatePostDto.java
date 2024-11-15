package com.posts.posts_service.application.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdatePostDto {
    private String title;
    private String content;
    private String excerpt;
}
