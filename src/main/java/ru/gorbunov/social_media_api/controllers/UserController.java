package ru.gorbunov.social_media_api.controllers;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.gorbunov.social_media_api.dto.AddPostDto;
import ru.gorbunov.social_media_api.dto.JwtRequest;
import ru.gorbunov.social_media_api.dto.PostDto;
import ru.gorbunov.social_media_api.mappers.PostMapper;
import ru.gorbunov.social_media_api.models.User;
import ru.gorbunov.social_media_api.services.UserService;

import java.util.Collections;

@RestController
@RequestMapping("/users")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional
public class UserController {
    UserService userService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PostDto addPost(@RequestBody @Valid AddPostDto postAddDto) {
        log.info("UserController: Request to add a new post: {}", postAddDto.toString());
        return PostMapper.toDto(userService.addNewPost(postAddDto));
    }

}
