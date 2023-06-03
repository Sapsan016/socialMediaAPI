package ru.gorbunov.social_media_api.security.jwt;

import ru.gorbunov.social_media_api.enums.Status;
import ru.gorbunov.social_media_api.models.User;

public final class JwtUserFactory {
    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getStatus().equals(Status.ACTIVE),
                user.getUpdated(),
                null
        );
    }
}
