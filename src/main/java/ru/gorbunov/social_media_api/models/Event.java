package ru.gorbunov.social_media_api.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.gorbunov.social_media_api.enums.EventType;
import ru.gorbunov.social_media_api.enums.Operation;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    Long id;

    LocalDateTime timestamp;

    @Column(name = "user_id")
    Long userId;

    @Enumerated(EnumType.STRING)
    EventType eventType;

    @Enumerated(EnumType.STRING)
    Operation operation;

    @Column(name = "entity_id")
    Long entityId;

}
