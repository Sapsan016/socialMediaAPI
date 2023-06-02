package ru.gorbunov.social_media_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gorbunov.social_media_api.models.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
