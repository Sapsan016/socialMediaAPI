package ru.gorbunov.social_media_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gorbunov.social_media_api.models.Role;

public interface RolesRepository extends JpaRepository<Role, Long> {
}
