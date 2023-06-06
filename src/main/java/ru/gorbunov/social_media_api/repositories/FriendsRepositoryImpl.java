package ru.gorbunov.social_media_api.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.gorbunov.social_media_api.enums.FriendshipStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
    }

    @Override
    public List<Long> getFriendsIds(Long userId) {
        String sqlQuery = "SELECT friend_id FROM user_friends where user_id = ? and friendship not in ('CANCELED')";
        return jdbcTemplate.query(sqlQuery, FriendsRepositoryImpl::mapRowToId, userId);
    }

    @Override
    public boolean isFriends(Long userId) {
        String sqlQuery = "SELECT friend_id FROM user_friends where user_id = ?";
        return !jdbcTemplate.queryForList(sqlQuery, Long.class, userId).isEmpty();
    }

    @Override
    public boolean isCanceled(Long userId) {
        String sqlQuery = "SELECT friend_id FROM user_friends where user_id = ? and friendship like 'CANCELED'";
        return !jdbcTemplate.queryForList(sqlQuery, Long.class, userId).isEmpty();
    }

    public static Long mapRowToId(ResultSet resultSet, int rowNum) throws SQLException {
        return (resultSet.getLong("friend_id"));
    }
}
