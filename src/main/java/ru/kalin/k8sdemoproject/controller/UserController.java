package ru.kalin.k8sdemoproject.controller;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.kalin.k8sdemoproject.model.CreateUserRequest;
import ru.kalin.k8sdemoproject.model.UserResponse;
import ru.kalin.k8sdemoproject.service.UserService;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class UserController implements UsersApiDelegate {
    private final UserService userService;

    @Override
    public ResponseEntity<UserResponse> createUser(
        CreateUserRequest createUserRequest
    ) {
        return ResponseEntity.ok(userService.createUser(createUserRequest));
    }

    @Override
    public ResponseEntity<UserResponse> getUser(UUID userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @Override
    public ResponseEntity<List<UserResponse>> getUsers(
        @Nullable Integer limit,
        @Nullable Integer offset
    ) {
        return ResponseEntity.ok(userService.getUsers(limit, offset));
    }
}
