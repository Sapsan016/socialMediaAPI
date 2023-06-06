package ru.gorbunov.social_media_api.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gorbunov.social_media_api.dto.AddPostDto;
import ru.gorbunov.social_media_api.enums.EventType;
import ru.gorbunov.social_media_api.enums.Operation;
import ru.gorbunov.social_media_api.exception.ObjectNotFoundException;
import ru.gorbunov.social_media_api.mappers.PostMapper;
import ru.gorbunov.social_media_api.models.Event;
import ru.gorbunov.social_media_api.models.Post;
import ru.gorbunov.social_media_api.models.User;
import ru.gorbunov.social_media_api.repositories.EventRepository;
import ru.gorbunov.social_media_api.repositories.PostRepository;
import ru.gorbunov.social_media_api.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostServiceImpl implements PostService{

    PostRepository postRepository;
    UserRepository userRepository;
    EventRepository eventRepository;
    static String UP = "OLD";

    @Override
    public Post addNewPost(AddPostDto postAddDto, Long userId) {
        Post postToAdd = PostMapper.toPost(postAddDto);
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ObjectNotFoundException(String.format("User with ID = %s was not found", userId)));
        postToAdd.setUser(user);
        postRepository.save(postToAdd);
        eventRepository.save(new Event(null, postToAdd.getCreated(), user, EventType.POST,
                Operation.ADD, postToAdd.getId()));
        log.info("Added post with ID = {}", postToAdd.getId());
        return postToAdd;
    }

    @Override
    public Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() ->
                new ObjectNotFoundException(String.format("Post with ID = %s was not found", postId)));
    }

    @Override
    public List<Post> findUserPosts(Long userId, Integer from, Integer size, String sort) {
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
    public Post updatePost(Long postId, AddPostDto addPostDto, Long userId) {
        Post postToUpdate = findPostById(postId);
        if (checkPostUser(postToUpdate, userId)) {
            throw new IllegalArgumentException(String.format("User with ID = %d was not author of the post", userId));
        }
        checkUpdate(postToUpdate, addPostDto);
        postRepository.save(postToUpdate);
        eventRepository.save(new Event(null, LocalDateTime.now(), postToUpdate.getUser(),
                EventType.POST, Operation.UPDATE, postId));
        log.info("Updated post with ID = {}", postId);
        return postToUpdate;
    }

    @Override
    public void removePost(Long postId, Long userId) {
        Post postToRemove = findPostById(postId);
        if (checkPostUser(postToRemove, userId)) {
            throw new IllegalArgumentException(String.format("User with ID = %d was not author of the post", userId));
        }
        postRepository.delete(postToRemove);
        eventRepository.save(new Event(null, LocalDateTime.now(), postToRemove.getUser(),
                EventType.POST, Operation.REMOVE, postId));
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
    private boolean checkPostUser(Post post, Long userId) {
        return !post.getUser().getId().equals(userId);
    }
}