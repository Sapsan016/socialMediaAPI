package ru.gorbunov.social_media_api.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.gorbunov.social_media_api.enums.FriendshipStatus;

@Repository
@RequiredArgsConstructor
public class FriendsRepositoryImpl implements FriendsRepository {

    private final JdbcTemplate jdbcTemplate;
    @Override
    public void addToFriends(Long userId, Long friendId) {
        String sqlQuery = "insert into user_friends(user_id, friend_id, friendship) values (?, ?, ?)";
        jdbcTemplate.update(sqlQuery, userId, friendId, FriendshipStatus.PENDING);
    }

    @Override
    public void confirmOrRejectFriendship(Long userId, Long friendId, FriendshipStatus friendshipStatus) {
        String sqlQuery = "UPDATE user_friends SET friendship = ? where USER_ID = ? and FRIEND_ID = ? ";
        jdbcTemplate.update(sqlQuery, friendshipStatus, userId, friendId);

    }
}
