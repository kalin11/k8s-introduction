package ru.kalin.k8sdemoproject.controller;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.kalin.k8sdemoproject.model.CreatePostRequest;
import ru.kalin.k8sdemoproject.model.CreatePostResponse;
import ru.kalin.k8sdemoproject.model.Post;
import ru.kalin.k8sdemoproject.model.UserPostsResponse;
import ru.kalin.k8sdemoproject.service.PostService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class PostController implements PostsApiDelegate {
    private final PostService postService;

    @Override
    public ResponseEntity<Post> likePost(UUID postId) {
        return ResponseEntity.ok(postService.likePost(postId));
    }

    @Override
    public ResponseEntity<Post> dislikePost(UUID postId) {
        return ResponseEntity.ok(postService.dislikePost(postId));
    }

    @Override
    public ResponseEntity<UserPostsResponse> getUserPosts(
        UUID userId,
        @Nullable Integer limit,
        @Nullable Integer offset
    ) {
        return ResponseEntity.ok(postService.getUserPosts(userId, limit, offset));
    }

    @Override
    public ResponseEntity<Post> getPost(UUID postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @Override
    public ResponseEntity<CreatePostResponse> createPost(
        CreatePostRequest createPostRequest
    ) {
        return ResponseEntity.ok(postService.createPost(createPostRequest));
    }
}
