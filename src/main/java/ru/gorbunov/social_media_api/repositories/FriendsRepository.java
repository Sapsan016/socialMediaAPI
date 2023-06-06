package ru.gorbunov.social_media_api.repositories;

import ru.gorbunov.social_media_api.enums.FriendshipStatus;

import java.util.List;

public interface FriendsRepository {
    void addToFriends(Long id, Long friendId);

    void confirmOrRejectFriendship(Long userId, Long friendId, String friendshipStatus);

    List<Long> getFriendsIds(Long userId);

    boolean isFriends(Long userId);

    public boolean isCanceled(Long userId);


}
