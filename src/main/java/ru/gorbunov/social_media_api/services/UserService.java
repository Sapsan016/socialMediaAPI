package ru.gorbunov.social_media_api.services;

import ru.gorbunov.social_media_api.dto.AddPostDto;
import ru.gorbunov.social_media_api.models.Post;
import ru.gorbunov.social_media_api.models.User;

public interface UserService {
    Post addNewPost(AddPostDto postAddDto);

    User addNewUser(User user);
}
