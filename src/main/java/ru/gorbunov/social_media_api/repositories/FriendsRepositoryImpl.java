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
        jdbcTemplate.update(sqlQuery, userId, friendId, FriendshipStatus.PENDING.toString());
    }

    @Override
    public void confirmOrRejectFriendship(Long userId, Long friendId, String friendshipStatus) {
        String sqlQuery = "UPDATE user_friends SET friendship = ? where user_id = ? and friend_id = ?";
     jdbcTemplate.update(sqlQuery, friendshipStatus, userId, friendId);

   //     jdbcTemplate.update("UPDATE user_friends SET friendship = 'TEST' where user_id = 2 and friend_id = 1");


    }
}
