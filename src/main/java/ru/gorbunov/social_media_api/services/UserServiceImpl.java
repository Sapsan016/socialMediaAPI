package ru.gorbunov.social_media_api.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gorbunov.social_media_api.dto.AddPostDto;
import ru.gorbunov.social_media_api.mappers.PostMapper;
import ru.gorbunov.social_media_api.models.Post;
import ru.gorbunov.social_media_api.repositories.PostRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    PostRepository postRepository;

    @Override
    public Post addNewPost(AddPostDto postAddDto) {
        Post postToAdd = PostMapper.toPost(postAddDto);
        postRepository.save(postToAdd);
        log.info("Added student with ID = {}", postToAdd.getId());
        return postToAdd;
    }
}
