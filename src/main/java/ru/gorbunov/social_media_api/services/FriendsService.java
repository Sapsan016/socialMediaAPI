package ru.gorbunov.social_media_api.services;

import ru.gorbunov.social_media_api.enums.FriendshipStatus;

public interface FriendsService {
    void addToFriends(Long userId, Long friendId);

    void confirmFriendship(Long userId, Long friendId, FriendshipStatus friendshipStatus);

    void rejectFriendship(Long userId, Long friendId, FriendshipStatus friendshipStatus);
}
