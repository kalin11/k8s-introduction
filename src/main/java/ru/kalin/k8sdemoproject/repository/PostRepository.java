package ru.kalin.k8sdemoproject.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kalin.k8sdemoproject.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    @Query("""
        INSERT INTO posts
        (id, content, created_at, user_id)
        VALUES (:id, :content, :createdAt, :userId)
        ON CONFLICT (id) DO NOTHING
        RETURNING *
    """)
    Post createPost(UUID id, String content, LocalDateTime createdAt, UUID userId);

    @Query("""
        UPDATE posts
        SET likes = likes + 1
        WHERE id = :id
        RETURNING *
    """)
    Post likePost(UUID id);

    @Query("""
        UPDATE posts
        SET dislikes = dislikes + 1
        WHERE id = :id
        RETURNING *
    """)
    Post dislikePost(UUID id);

    @Query("""
        SELECT *
        FROM posts
        WHERE id = :id
    """)
    Optional<Post> findPostById(UUID id);

    @Query("""
        SELECT *
        FROM posts
        WHERE user_id = :userId
        OFFSET :offset
        LIMIT :limit
    """)
    List<Post> findPostsByUserId(UUID userId, Integer offset, Integer limit);

    @Query("""
        SELECT EXISTS(
            SELECT 1
            FROM posts
            WHERE id = :id
        )
    """)
    boolean existsPostById(UUID id);
}
