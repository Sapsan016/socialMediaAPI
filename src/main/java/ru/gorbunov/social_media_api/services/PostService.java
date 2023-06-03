package ru.gorbunov.social_media_api.services;

import ru.gorbunov.social_media_api.dto.AddPostDto;
import ru.gorbunov.social_media_api.enums.FriendshipStatus;
import ru.gorbunov.social_media_api.models.Post;

import java.util.List;

public interface PostService {
    Post addNewPost(AddPostDto postAddDto, Long userId);

    Post findPostById(Long postId);

    List<Post> findUserPosts(String userId, Integer from, Integer size, String sort);

    Post updatePost(Long postId, AddPostDto addPostDto);

    void removePost(Long postId);

    void addToFriends(Long userId, Long friendId);

    void confirmOrRejectFriendship(Long userId, Long friendId, FriendshipStatus friendshipStatus);
}
