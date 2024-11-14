package com.posts.posts_service.application.service;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.posts.posts_service.adapter.out.PostsRepository;
import com.posts.posts_service.application.dto.request.CreatePostDto;
import com.posts.posts_service.application.dto.request.UpdatePostDto;
import com.posts.posts_service.domain.exception.ResourceNotFound;
import com.posts.posts_service.domain.exception.Unauthorized;
import com.posts.posts_service.domain.models.Post;

@Service
public class PostsService {

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Post> getPosts() {
        return postsRepository.findAll();
    }

    public List<Post> getPostByAuthor(String userId) {
        return postsRepository.findByUserId(userId);
    }

    public Post createPost(CreatePostDto createPostDto) {
        Post newPost = postsRepository.save(modelMapper.map(createPostDto, Post.class));
        return newPost;
    }

    public Post getPost(String postId) {
        UUID id = UUID.fromString(postId);
        return postsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Post with id: " + postId + " not found"));
    }

    public Post updatePost(UpdatePostDto updatePostDto, String postId, String userId) {
        UUID id = UUID.fromString(postId);
        Post targetPost = postsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Post with id: " + postId + " not found"));

        if (!userId.equals(targetPost.getAuthor().getUserId()))
            throw new Unauthorized("You're unauthorized to edit this post");

        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(updatePostDto, targetPost);

        return postsRepository.save(targetPost);
    }

    public void deletePost(String postId, String userId) {
        UUID id = UUID.fromString(postId);
        Post targetPost = postsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Post with id: " + postId + " not found"));

        if (!userId.equals(targetPost.getAuthor().getUserId()))
            throw new Unauthorized("You're unauthorized to delete this post");

        postsRepository.delete(targetPost);
    }

}
