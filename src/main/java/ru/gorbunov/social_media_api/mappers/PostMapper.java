package ru.gorbunov.social_media_api.mappers;

import ru.gorbunov.social_media_api.dto.AddPostDto;
import ru.gorbunov.social_media_api.dto.PostDto;
import ru.gorbunov.social_media_api.models.Post;

import java.time.LocalDateTime;

public class PostMapper {
    public static Post toPost(AddPostDto addDto) {
        return new Post(
                null,
                addDto.getHeader(),
                addDto.getText(),
                addDto.getImageRef(),
                LocalDateTime.now()
        );
    }

    public static PostDto toDto(Post post) {
        return new PostDto(
                post.getId(),
                post.getHeader(),
                post.getText(),
                post.getImageRef(),
                post.getCreated()
        );
    }
}
