package ru.gorbunov.social_media_api.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.gorbunov.social_media_api.enums.RoleName;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    RoleName name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    List<User> users;

}
