package ru.gorbunov.social_media_api.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Fetch;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password;

    @Column(name = "email")
    String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
    joinColumns =  {@JoinColumn(name = "user_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    List<Role> roles;

}
