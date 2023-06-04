package ru.gorbunov.social_media_api.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gorbunov.social_media_api.dto.AddPostDto;
import ru.gorbunov.social_media_api.enums.EventType;
import ru.gorbunov.social_media_api.enums.FriendshipStatus;
import ru.gorbunov.social_media_api.enums.Operation;
import ru.gorbunov.social_media_api.exception.ObjectNotFoundException;
import ru.gorbunov.social_media_api.exception.ValidationException;
import ru.gorbunov.social_media_api.mappers.PostMapper;
import ru.gorbunov.social_media_api.models.Event;
import ru.gorbunov.social_media_api.models.Post;
import ru.gorbunov.social_media_api.repositories.EventRepository;
import ru.gorbunov.social_media_api.repositories.FriendsRepository;
import ru.gorbunov.social_media_api.repositories.PostRepository;
import ru.gorbunov.social_media_api.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostServiceImpl implements PostService{

    PostRepository postRepository;
    FriendsRepository friendsRepository;
    UserRepository userRepository;
    EventRepository eventRepository;
    static String UP = "OLD";

    @Override
    public Post addNewPost(AddPostDto postAddDto, Long userId) {
        Post postToAdd = PostMapper.toPost(postAddDto);
        postToAdd.setUser(userRepository.findById(userId).get());
        postRepository.save(postToAdd);
        eventRepository.save(new Event(null, postToAdd.getCreated(), userId, EventType.POST,
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
    public Post updatePost(Long postId, AddPostDto addPostDto) {
        Post postToUpdate = findPostById(postId);
        checkUpdate(postToUpdate, addPostDto);
        postRepository.save(postToUpdate);
        eventRepository.save(new Event(null, LocalDateTime.now(), postToUpdate.getUser().getId(),
                EventType.POST, Operation.UPDATE, postId));
        log.info("Updated post with ID = {}", postId);
        return postToUpdate;
    }

    @Override
    public void removePost(Long postId) {
        Post postToRemove = findPostById(postId);
        postRepository.delete(postToRemove);
        eventRepository.save(new Event(null, LocalDateTime.now(), postToRemove.getUser().getId(),
                EventType.POST, Operation.REMOVE, postId));
        log.info("Removed post with ID = {}", postId);
    }

    @Override
    public void addToFriends(Long userId, Long friendId) {
        checkFriends(userId, friendId);
        friendsRepository.addToFriends(userId, friendId);
        eventRepository.save(new Event(null, LocalDateTime.now(), userId, EventType.FRIEND, Operation.ADD, friendId));
        log.info("User with ID = {} added friend with ID = {}", userId, friendId);
    }

    @Override
    public void confirmOrRejectFriendship(Long userId, Long friendId, FriendshipStatus friendshipStatus) {
        checkFriends(userId, friendId);
        friendsRepository.confirmOrRejectFriendship(userId,friendId, friendshipStatus);
        if (friendshipStatus.equals(FriendshipStatus.CONFIRMED)) {
            eventRepository.save(new Event(null, LocalDateTime.now(), userId, EventType.FRIEND,
                    Operation.CONFIRM, friendId));
        }
        else {
            eventRepository.save(new Event(null, LocalDateTime.now(), userId, EventType.FRIEND,
                    Operation.REJECT, friendId));
        }
        log.info("User with ID = {} {} friendship with user ID = {}", userId, friendshipStatus, friendId);
    }



    private void checkUpdate(Post postToUpdate, AddPostDto addPostDto) {
        if (addPostDto.getHeader() != null)
            postToUpdate.setHeader(addPostDto.getHeader());
        if (addPostDto.getText() != null)
            postToUpdate.setText(addPostDto.getText());
        if (addPostDto.getImageRef() != null)
            postToUpdate.setImageRef(addPostDto.getImageRef());
    }

    private void checkFriends(Long userId, Long friendId) {
        if (Objects.equals(userId, friendId)) {
            throw new ValidationException("You cannot add yourself!");
        }
        if (userRepository.findById(friendId).isEmpty()) {
            throw new ObjectNotFoundException(String.format("User with ID = %s was not found", friendId));
        }
    }

}