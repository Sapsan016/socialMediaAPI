package ru.gorbunov.social_media_api.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequestDto {
    String username;
    String password;

}
