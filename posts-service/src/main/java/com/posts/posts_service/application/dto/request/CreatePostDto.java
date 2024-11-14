package com.posts.posts_service.application.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.posts.posts_service.adapter.in.UserContext;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreatePostDto {
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    private String excerpt;
    public String getExcerpt() {
        return excerpt;
    }
    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }
    private UserContext author;
    public UserContext getAuthor() {
        return author;
    }
    public void setAuthor(UserContext author) {
        this.author = author;
    }
}
