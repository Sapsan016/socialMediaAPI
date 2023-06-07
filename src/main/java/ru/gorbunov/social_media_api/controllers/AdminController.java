package ru.gorbunov.social_media_api.controllers;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gorbunov.social_media_api.dto.UserDto;
import ru.gorbunov.social_media_api.mappers.UserMapper;
import ru.gorbunov.social_media_api.models.User;
import ru.gorbunov.social_media_api.services.UserService;


@RestController
@RequestMapping(value = "/api/v2/admin/")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminController {
    UserService userService;

    @GetMapping("users/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable() Long userId) {
        User user = userService.findById(userId);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        UserDto result = UserMapper.toDto(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("users/delete/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
    }



}