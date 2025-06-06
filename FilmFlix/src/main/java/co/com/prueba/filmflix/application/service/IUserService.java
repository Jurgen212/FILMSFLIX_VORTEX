package co.com.prueba.filmflix.application.service;

import co.com.prueba.filmflix.domain.dto.user.CreateUserRequest;
import co.com.prueba.filmflix.domain.dto.user.UpdateUserRequest;
import co.com.prueba.filmflix.domain.dto.user.UserResponse;

import java.util.List;

public interface IUserService {
    List<UserResponse> findAll();
    List<UserResponse> findAllEnabled();
    UserResponse updateRol(Long id);
    UserResponse findById(Long id);
    UserResponse findByEmail(String email);
    UserResponse save(CreateUserRequest createUserRequest);
    UserResponse update(UpdateUserRequest updateUserRequest);
    void delete(Long id);
}
