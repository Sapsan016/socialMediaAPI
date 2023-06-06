package ru.gorbunov.social_media_api.models;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.gorbunov.social_media_api.enums.EventType;
import ru.gorbunov.social_media_api.enums.Operation;
import ru.gorbunov.social_media_api.enums.Status;

import javax.persistence.*;
import java.util.List;
import javax.persistence.Column;
import java.util.Date;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "events")
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    Long id;

    LocalDateTime timestamp;

    @JoinColumn(name = "user_id")
    @ManyToOne
    User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    EventType eventType;

    @Enumerated(EnumType.STRING)
    Operation operation;

    @Column(name = "entity_id")
    Long entityId;

}
