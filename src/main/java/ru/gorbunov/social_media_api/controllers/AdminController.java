package ru.gorbunov.social_media_api.controllers;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gorbunov.social_media_api.dto.UserDto;
import ru.gorbunov.social_media_api.mappers.UserMapper;
import ru.gorbunov.social_media_api.models.User;
import ru.gorbunov.social_media_api.services.UserService;


@RestController
@RequestMapping(value = "/api/v1/admin/")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminController {
    UserService userService;

    @GetMapping(value = "users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        UserDto result = UserMapper.toDto(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}