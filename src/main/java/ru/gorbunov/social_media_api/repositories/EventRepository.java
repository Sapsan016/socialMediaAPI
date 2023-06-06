package ru.gorbunov.social_media_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gorbunov.social_media_api.enums.Operation;
import ru.gorbunov.social_media_api.models.Event;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "SELECT * FROM events WHERE user_id = ?", nativeQuery = true)
    List<Event> findAllByUserId(Long userId);


}
