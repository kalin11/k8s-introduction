package ru.kalin.k8sdemoproject.service;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kalin.k8sdemoproject.model.CreateUserRequest;
import ru.kalin.k8sdemoproject.model.UserResponse;
import ru.kalin.k8sdemoproject.properties.UsersProperties;
import ru.kalin.k8sdemoproject.repository.UserRepository;
import ru.kalin.k8sdemoproject.util.CursorUtil;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UsersProperties usersProperties;

    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        var userName = request.getName();
        var userSurname = request.getSurname();
        var uuid = UUID.randomUUID();

        var savedUser = userRepository.createUser(uuid, userName, userSurname);

        return new UserResponse()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .surname(savedUser.getSurname());
    }

    public UserResponse getUserById(UUID id) {
        var user = userRepository.getUserById(id);

        return new UserResponse()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname());
    }

    public List<UserResponse> getUsers(
        @Nullable Integer requestLimit,
        @Nullable Integer requestOffset
    ) {
        var cursor = CursorUtil.getCursor(
            requestLimit,
            requestOffset,
            usersProperties.getDefaultLimit(),
            usersProperties.getDefaultOffset()
        );

        var users = userRepository.getUsers(cursor.getLimit(), cursor.getOffset());

        return users.stream()
                .map(user -> new UserResponse()
                        .id(user.getId())
                        .name(user.getName())
                        .surname(user.getSurname())
                )
                .toList();
    }

    public boolean existsById(UUID id) {
        return userRepository.existsById(id);
    }
}
