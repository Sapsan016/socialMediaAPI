package ru.gorbunov.social_media_api.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gorbunov.social_media_api.dto.AddUserDto;
import ru.gorbunov.social_media_api.enums.Status;
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
        User result = userRepository.findByUsername(username);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById - user: {} found by id: {}", result, id);
        return result;
    }

//    @Override
//    public void delete(Long id) {
//        userRepository.deleteById(id);
//        log.info("IN delete - user with id: {} successfully deleted");
//    }
}