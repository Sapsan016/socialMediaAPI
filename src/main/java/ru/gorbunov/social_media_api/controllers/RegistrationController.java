package ru.gorbunov.social_media_api.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gorbunov.social_media_api.dto.AddUserDto;
import ru.gorbunov.social_media_api.dto.UserDto;
import ru.gorbunov.social_media_api.mappers.UserMapper;
import ru.gorbunov.social_media_api.services.UserService;


@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(value = "/api/v2/user")
@Slf4j
public class RegistrationController {

    UserService userService;

    @Transactional
    @PostMapping("/registration")

    public ResponseEntity<?> addUser(@RequestBody AddUserDto addUserDto) {
        log.info("RegistrationController: User register request: {}", addUserDto);
        UserDto result = UserMapper.toDto(userService.register(addUserDto));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}

