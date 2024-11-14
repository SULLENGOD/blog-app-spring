package com.posts.posts_service.adapter.in;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.posts.posts_service.application.dto.request.CreatePostDto;
import com.posts.posts_service.application.dto.request.UpdatePostDto;
import com.posts.posts_service.application.service.PostsService;
import com.posts.posts_service.domain.models.Post;

@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    private PostsService postsService;

    @GetMapping("")
    public ResponseEntity<List<Post>> getPosts() {
        return ResponseEntity.ok(postsService.getPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable String id) {
        return ResponseEntity.ok(postsService.getPost(id));
    }

    @GetMapping("/my-posts")
    public ResponseEntity<List<Post>> getPostsByAuthor(@RequestAttribute UserContext userContext) {
        String userId = userContext.getUserId();

        return ResponseEntity.ok(postsService.getPostByAuthor(userId));
    }

    @PostMapping("")
    public ResponseEntity<Post> createPost(@RequestBody CreatePostDto newPost,
            @RequestAttribute UserContext userContext) {

        UserContext targetAuthor = new UserContext();
        targetAuthor.setUserId(userContext.getUserId());
        targetAuthor.setUsername(userContext.getUsername());

        newPost.setAuthor(targetAuthor);

        Post createdPost = postsService.createPost(newPost);
        return ResponseEntity.ok(createdPost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(
            @PathVariable String id,
            @RequestBody UpdatePostDto updateData,
            @RequestAttribute UserContext userContext) {
        Post updatedPost = postsService.updatePost(updateData, id, userContext.getUserId());

        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable String id, @RequestAttribute UserContext userContext) {

        postsService.deletePost(id, userContext.getUserId());

        return ResponseEntity.ok("Post with id: " + id + "deleted successfuly.");
    }
}
