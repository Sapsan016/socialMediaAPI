package ru.gorbunov.social_media_api.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gorbunov.social_media_api.dto.AddPostDto;
import ru.gorbunov.social_media_api.exception.ObjectNotFoundException;
import ru.gorbunov.social_media_api.mappers.PostMapper;
import ru.gorbunov.social_media_api.models.Post;
import ru.gorbunov.social_media_api.repositories.PostRepository;
import ru.gorbunov.social_media_api.repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    PostRepository postRepository;

    UserRepository userRepository;
    static String UP = "OLD";

    @Override
    public Post addNewPost(AddPostDto postAddDto, Long userId) {
        Post postToAdd = PostMapper.toPost(postAddDto);
        postToAdd.setUser(userRepository.findById(userId).get());
        postRepository.save(postToAdd);
        log.info("Added student with ID = {}", postToAdd.getId());
        return postToAdd;
    }

    @Override
    public Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() ->
                new ObjectNotFoundException(String.format("Post with ID = %s was not found", postId)));
    }

    @Override
    public List<Post> findUserPosts(String userId, Integer from, Integer size, String sort) {
        if (sort.equals(UP)) {
            log.info("Getting posts list, created by user with ID = {}, sort by creation: {}, skip: {}, size: {}",
                    userId, sort, from, size);
            return postRepository.getAllStudentsSortAsc(userId, from, size);
        }
        log.info("Getting posts list, created by user with ID = {}, sort by creation: {}, skip: {}, size: {}",
                userId, sort, from, size);
        return postRepository.getAllStudentsSortDesc(userId, from, size);
    }

    @Override
    public Post updatePost(Long postId, AddPostDto addPostDto) {
        Post postToUpdate = findPostById(postId);
        checkUpdate(postToUpdate, addPostDto);
        postRepository.save(postToUpdate);
        log.info("Updated post with ID = {}", postId);
        return postToUpdate;
    }

    @Override
    public void removePost(Long postId) {
        Post studentToRemove = findPostById(postId);
        postRepository.delete(studentToRemove);
        log.info("Removed post with ID = {}", postId);
    }


    private void checkUpdate(Post postToUpdate, AddPostDto addPostDto) {
        if (addPostDto.getHeader() != null)
            postToUpdate.setHeader(addPostDto.getHeader());
        if (addPostDto.getText() != null)
            postToUpdate.setText(addPostDto.getText());
        if (addPostDto.getImageRef() != null)
            postToUpdate.setImageRef(addPostDto.getImageRef());
    }

}

