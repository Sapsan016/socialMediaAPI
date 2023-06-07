package ru.gorbunov.social_media_api.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;
import javax.persistence.Column;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    Long id;

    String header;
    String text;
    @Column(name = "image_ref")
    String imageRef;
    LocalDateTime created;

    @JoinColumn(name = "user_id")
    @ManyToOne
    User user;

}
