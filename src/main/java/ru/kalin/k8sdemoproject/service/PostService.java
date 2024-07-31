package ru.kalin.k8sdemoproject.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kalin.k8sdemoproject.exceptions.PostNotFoundException;
import ru.kalin.k8sdemoproject.exceptions.UserNotFoundException;
import ru.kalin.k8sdemoproject.model.CreatePostRequest;
import ru.kalin.k8sdemoproject.model.CreatePostResponse;
import ru.kalin.k8sdemoproject.model.Post;
import ru.kalin.k8sdemoproject.model.UserPostsResponse;
import ru.kalin.k8sdemoproject.properties.PostsProperties;
import ru.kalin.k8sdemoproject.repository.PostRepository;
import ru.kalin.k8sdemoproject.service.util.ClockService;
import ru.kalin.k8sdemoproject.util.CursorUtil;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostsProperties postsProperties;
    private final UserService userService;
    private final ClockService clock;

    @Transactional
    public CreatePostResponse createPost(CreatePostRequest createPostRequest) {
        var content = createPostRequest.getContent();
        var userId = createPostRequest.getUserId();
        var cratedAt = clock.now();

        var userExists = userService.existsById(userId);
        if (!userExists) {
            throw new UserNotFoundException("User not found");
        }

        var savedPost = postRepository.createPost(UUID.randomUUID(), content, cratedAt, userId);

        return new CreatePostResponse()
                .userId(savedPost.getUserId())
                .content(savedPost.getContent())
                .createdAt(savedPost.getCreatedAt());
    }

    public Post likePost(UUID postId) {
        var postExists = postRepository.existsPostById(postId);
        if (!postExists) {
            throw new PostNotFoundException("Post not found");
        }
        return postRepository.likePost(postId);
    }

    public Post dislikePost(UUID postId) {
        var postExists = postRepository.existsPostById(postId);
        if (!postExists) {
            throw new PostNotFoundException("Post not found");
        }
        return postRepository.dislikePost(postId);
    }

    public Post getPostById(UUID postId) {
        return postRepository.findPostById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));
    }

    public UserPostsResponse getUserPosts(
        UUID userId,
        @Nullable Integer requestLimit,
        @Nullable Integer requestOffset
    ) {

        var userExists = userService.existsById(userId);
        if (!userExists) {
            throw new UserNotFoundException("User not found");
        }

        var cursor = CursorUtil.getCursor(
                requestLimit,
                requestOffset,
                postsProperties.getDefaultLimit(),
                postsProperties.getDefaultOffset()
        );
        var user = userService.getUserById(userId);
        var userPosts = postRepository.findPostsByUserId(userId, cursor.getOffset(), cursor.getLimit());

        return new UserPostsResponse()
                .author(user)
                .posts(userPosts);
    }
}
