package ru.gorbunov.social_media_api.dto;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
    private String email;
}
