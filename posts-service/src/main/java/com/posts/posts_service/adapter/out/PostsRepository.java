package com.posts.posts_service.adapter.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.posts.posts_service.domain.models.Post;

public interface PostsRepository extends JpaRepository<Post, UUID> {
    @SuppressWarnings("null")
    @Override
    Optional<Post> findById(UUID id);

    @Query("SELECT p FROM Post p WHERE p.author.userId = :userId")
    List<Post> findByUserId(@Param("userId") String userId);
}
