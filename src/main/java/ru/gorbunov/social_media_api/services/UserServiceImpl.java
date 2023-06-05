package ru.gorbunov.social_media_api.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gorbunov.social_media_api.dto.AddUserDto;
import ru.gorbunov.social_media_api.enums.Status;
import ru.gorbunov.social_media_api.exception.ObjectNotFoundException;
import ru.gorbunov.social_media_api.mappers.UserMapper;
import ru.gorbunov.social_media_api.models.Role;
import ru.gorbunov.social_media_api.models.User;
import ru.gorbunov.social_media_api.repositories.*;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    RolesRepository roleRepository;
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public User register(AddUserDto addUserDto) {
        User user = UserMapper.toUser(addUserDto);
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

//    @Override
//    public List<User> getAll() {
//        List<User> result = userRepository.findAll();
//        log.info("IN getAll - {} users found", result.size());
//        return result;
//    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new ObjectNotFoundException(String.format("User with ID = %s was not found", username)));
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new ObjectNotFoundException(String.format("User with ID = %s was not found", userId)));
    }

//    @Override
//    public void delete(Long id) {
//        userRepository.deleteById(id);
//        log.info("IN delete - user with id: {} successfully deleted");
//    }
}