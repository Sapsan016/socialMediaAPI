package ru.gorbunov.social_media_api.services;

import ru.gorbunov.social_media_api.dto.AddPostDto;
import ru.gorbunov.social_media_api.dto.AddUserDto;
import ru.gorbunov.social_media_api.enums.FriendshipStatus;
import ru.gorbunov.social_media_api.models.Post;
import ru.gorbunov.social_media_api.models.User;

import java.util.Arrays;
import java.util.List;

public interface UserService {

    User register(AddUserDto addUserDto);

    User findByUsername(String username);

}
