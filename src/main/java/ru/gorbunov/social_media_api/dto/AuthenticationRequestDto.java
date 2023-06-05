package ru.gorbunov.social_media_api.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequestDto {
    @NotBlank
    String username;
    @NotNull
    String password;

}
