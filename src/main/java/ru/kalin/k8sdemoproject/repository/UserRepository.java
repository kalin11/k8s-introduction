package ru.kalin.k8sdemoproject.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kalin.k8sdemoproject.model.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    @Query("""
        INSERT INTO users
        (id, name, surname)
        VALUES (:id, :name, :surname)
        ON CONFLICT (id) DO NOTHING
        RETURNING *;
    """)
    User createUser(UUID id, String name, String surname);

    @Query("""
        SELECT *
        FROM users
        WHERE id::uuid = :id::uuid;
    """)
    User getUserById(UUID id);

    @Query("""
        SELECT *
        FROM users
        LIMIT :limit
        OFFSET :offset
    """)
    List<User> getUsers(Integer limit, Integer offset);

    @Query("""
        SELECT EXISTS (
            SELECT 1
            FROM users
            WHERE id = :id
        )
    """)
    boolean existsById(UUID id);

}
