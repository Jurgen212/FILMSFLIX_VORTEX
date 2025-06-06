package co.com.prueba.filmflix.domain.mappers;

import co.com.prueba.filmflix.domain.dto.user.CreateUserRequest;
import co.com.prueba.filmflix.domain.dto.user.UpdateUserRequest;
import co.com.prueba.filmflix.domain.dto.user.UserResponse;
import co.com.prueba.filmflix.domain.entities.User;
import co.com.prueba.filmflix.utils.enums.Rol;

import java.util.List;

public class UserMapper {
    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .role(user.getRole().toString())
                .enabled(user.isEnabled())
                .build();
    }

    public static User toUser(CreateUserRequest createUserRequest, String password) {
        return User.builder()
                .name(createUserRequest.getName())
                .lastName(createUserRequest.getLastName())
                .phoneNumber(createUserRequest.getPhoneNumber())
                .email(createUserRequest.getEmail())
                .password(password)
                .build();
    }

    public static User toUser(UpdateUserRequest updateUserRequest, String password, Rol role, boolean enabled) {
        return User.builder()
                .id(updateUserRequest.getId())
                .name(updateUserRequest.getName())
                .lastName(updateUserRequest.getLastName())
                .phoneNumber(updateUserRequest.getPhoneNumber())
                .email(updateUserRequest.getEmail())
                .password(password)
                .role(role)
                .enabled(enabled)
                .build();
    }

    public static List<UserResponse> toUserResponseList(List<User> users) {
        return users.stream().map(UserMapper::toUserResponse).toList();
    }
}
