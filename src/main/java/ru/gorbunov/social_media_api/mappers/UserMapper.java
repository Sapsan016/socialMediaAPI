package ru.gorbunov.social_media_api.mappers;

import ru.gorbunov.social_media_api.dto.AddPostDto;
import ru.gorbunov.social_media_api.dto.AddUserDto;
import ru.gorbunov.social_media_api.dto.PostDto;
import ru.gorbunov.social_media_api.dto.UserDto;
import ru.gorbunov.social_media_api.enums.Status;
import ru.gorbunov.social_media_api.models.Post;
import ru.gorbunov.social_media_api.models.User;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class UserMapper {
    public static User toUser(AddUserDto addUserDto) {
        return new User(
                null,
                addUserDto.getUsername(),
                addUserDto.getPassword(),
                addUserDto.getEmail(),
                Date.from(Instant.now()),
                Status.ACTIVE,
                null
        );
    }

    public static UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getStatus()
        );
    }
}