package ru.gorbunov.social_media_api.models;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.gorbunov.social_media_api.enums.EventType;
import ru.gorbunov.social_media_api.enums.Operation;
import ru.gorbunov.social_media_api.enums.Status;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    @Column(name = "user_id")
    Long userId;

    @Enumerated(EnumType.STRING)
    EventType eventType;

    @Enumerated(EnumType.STRING)
    Operation operation;

    @Column(name = "entity_id")
    Long entityId;

}
