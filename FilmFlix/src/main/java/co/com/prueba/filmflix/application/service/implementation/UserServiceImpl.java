package co.com.prueba.filmflix.application.service.implementation;

import co.com.prueba.filmflix.application.repository.UserRepository;
import co.com.prueba.filmflix.application.service.IUserService;
import co.com.prueba.filmflix.domain.dto.user.CreateUserRequest;
import co.com.prueba.filmflix.domain.dto.user.UpdateUserRequest;
import co.com.prueba.filmflix.domain.dto.user.UserResponse;
import co.com.prueba.filmflix.domain.entities.User;
import co.com.prueba.filmflix.domain.mappers.UserMapper;
import co.com.prueba.filmflix.utils.enums.Rol;
import co.com.prueba.filmflix.utils.exceptions.ConflictException;
import co.com.prueba.filmflix.utils.exceptions.NotFoundException;
import co.com.prueba.filmflix.utils.validations.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return UserMapper.toUserResponseList(userRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findAllEnabled() {
        return UserMapper.toUserResponseList(userRepository.findAllByEnabledTrue());
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findById(Long id) {
        if(!userRepository.existsById(id)) throw new NotFoundException(String.format(UserValidator.USER_NOT_FOUND, id));
        return UserMapper.toUserResponse(userRepository.getReferenceById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findByEmail(String email) {
        if(!userRepository.existsByEmail(email)) throw new NotFoundException(String.format(UserValidator.USER_NOT_FOUND_BY_EMAIL, email));
        return UserMapper.toUserResponse(userRepository.findByEmail(email));
    }

    @Override
    @Transactional
    public UserResponse save(CreateUserRequest createUserRequest) {
        if(userRepository.existsByEmail(createUserRequest.getEmail())) throw new NotFoundException(String.format(UserValidator.EMAIL_ALREADY_EXISTS, createUserRequest.getEmail()));
        if(userRepository.existsByPhoneNumber(createUserRequest.getPhoneNumber())) throw new NotFoundException(String.format(UserValidator.PHONE_NUMBER_ALREADY_EXISTS, createUserRequest.getPhoneNumber()));

        return UserMapper.toUserResponse(userRepository.save(UserMapper.toUser(createUserRequest, bCryptPasswordEncoder.encode(createUserRequest.getPassword()))));
    }

    @Override
    @Transactional
    public UserResponse update(UpdateUserRequest updateUserRequest) {
        if(!userRepository.existsById(updateUserRequest.getId())) throw new NotFoundException(String.format(UserValidator.USER_NOT_FOUND, updateUserRequest.getId()));

        User oldUser = userRepository.getReferenceById(updateUserRequest.getId());
        if(!oldUser.getEmail().equals(updateUserRequest.getEmail()) && userRepository.existsByEmail(updateUserRequest.getEmail())) throw new ConflictException(String.format(UserValidator.EMAIL_ALREADY_EXISTS, updateUserRequest.getEmail()));
        if(!oldUser.getPhoneNumber().equals(updateUserRequest.getPhoneNumber()) && userRepository.existsByPhoneNumber(updateUserRequest.getPhoneNumber())) throw new ConflictException(String.format(UserValidator.PHONE_NUMBER_ALREADY_EXISTS, updateUserRequest.getPhoneNumber()));

        return UserMapper.toUserResponse(userRepository.save(UserMapper.toUser(updateUserRequest, bCryptPasswordEncoder.encode(updateUserRequest.getPassword()), oldUser.getRole(), oldUser.isEnabled())));
    }

    @Override
    @Transactional
    public UserResponse updateRol(Long id) {
        if(!userRepository.existsById(id)) throw new NotFoundException(String.format(UserValidator.USER_NOT_FOUND, id));

        User user = userRepository.getReferenceById(id);
        if (user.getRole() == Rol.USER) {
            user.setRole(Rol.ADMIN);
        } else {
            user.setRole(Rol.USER);
        }

        return UserMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(!userRepository.existsById(id)) throw new NotFoundException(String.format(UserValidator.USER_NOT_FOUND, id));

        User user = userRepository.getReferenceById(id);
        user.setEnabled(false);

        userRepository.save(user);
    }
}
