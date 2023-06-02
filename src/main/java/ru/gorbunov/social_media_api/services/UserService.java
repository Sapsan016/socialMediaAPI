package ru.gorbunov.social_media_api.services;

import ru.gorbunov.social_media_api.dto.AddPostDto;
import ru.gorbunov.social_media_api.models.Post;

public interface UserService {
    Post addNewPost(AddPostDto postAddDto);
}
