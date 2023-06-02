package ru.gorbunov.social_media_api.repositories;

public interface FriendsRepository {
    void addToFriends(Long id, Long friendId);
}
