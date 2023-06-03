package ru.gorbunov.social_media_api.repositories;

import ru.gorbunov.social_media_api.enums.FriendshipStatus;

public interface FriendsRepository {
    void addToFriends(Long id, Long friendId);

    void confirmOrRejectFriendship(Long userId, Long friendId, FriendshipStatus friendshipStatus);
}
