package ru.gorbunov.social_media_api.mappers;

import ru.gorbunov.social_media_api.dto.EventDto;
import ru.gorbunov.social_media_api.models.Event;

public class EventMapper {
    public static EventDto toDto(Event event) {
        return new EventDto(
                event.getId(),
                event.getTimestamp(),
                UserMapper.toDto(event.getUser()),
                event.getEventType(),
                event.getOperation(),
                event.getEntityId()
        );
    }
}
