package ru.gorbunov.social_media_api.services;

import ru.gorbunov.social_media_api.models.Event;

import java.util.List;

public interface FriendsService {
    void addToFriends(Long userId, Long friendId);

    void confirmFriendship(Long userId, Long friendId, String friendshipStatus);

    void rejectFriendship(Long userId, Long friendId, String friendshipStatus);

    void checkFriendshipResponse(Long userId, Long friendId, String friendshipStatus);

    List<Event> getUserFeed(Long userId, int from, int size);

    void cancelFriendship(Long userId, Long friendId);
}
