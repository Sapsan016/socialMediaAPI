package ru.gorbunov.social_media_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gorbunov.social_media_api.models.Event;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
//    @Query(value = "SELECT * FROM students ORDER BY student_rate DESC offset ? LIMIT ?", nativeQuery = true)
//    List<Event> getAllFriendsEventsSortDesc(Integer from, Integer size);
    @Query(value = "SELECT * FROM events WHERE user_id = ?", nativeQuery = true)
    List<Event> findAllByUserId(Long userId);
}
