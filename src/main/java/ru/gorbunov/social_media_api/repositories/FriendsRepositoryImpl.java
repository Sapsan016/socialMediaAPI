package ru.gorbunov.social_media_api.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FriendsRepositoryImpl implements FriendsRepository {

    private final JdbcTemplate jdbcTemplate;
    @Override
    public void addToFriends(Long id, Long friendId) {
        String sqlQuery = "insert into user_friends(user_id, friend_id) values (?, ?)";
        jdbcTemplate.update(sqlQuery, id, friendId);
    }
}
