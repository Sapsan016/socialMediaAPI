package ru.gorbunov.social_media_api.services;

import ru.gorbunov.social_media_api.dto.AddPostDto;
import ru.gorbunov.social_media_api.models.Post;

import java.util.List;

public interface PostService {
    Post addNewPost(AddPostDto postAddDto, Long userId);

    Post findPostById(Long postId);

    List<Post> findUserPosts(Long userId, Integer from, Integer size, String sort);

    Post updatePost(Long postId, AddPostDto addPostDto, Long userId);

    void removePost(Long postId, Long userId);


}
