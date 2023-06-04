package ru.gorbunov.social_media_api.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.gorbunov.social_media_api.dto.AddPostDto;
import ru.gorbunov.social_media_api.dto.PostDto;
import ru.gorbunov.social_media_api.enums.FriendshipStatus;
import ru.gorbunov.social_media_api.mappers.PostMapper;
import ru.gorbunov.social_media_api.services.PostService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional
public class UserController {
    PostService postService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{userId}")
    public PostDto addPost(@RequestBody @Valid AddPostDto postAddDto, @PathVariable Long userId) {
        log.info("UserController: Request to add a new post: {} by user with ID = {}", postAddDto.toString(), userId);
        return PostMapper.toDto(postService.addNewPost(postAddDto, userId));
    }

    @GetMapping("/{postId}")
    public PostDto getPostById(@PathVariable Long postId) {
        log.info("UserController: Request to find a post wit ID = {}", postId);
        return PostMapper.toDto(postService.findPostById(postId));
    }


    @GetMapping("/{userId}")
    public List<PostDto> getUserPosts(@RequestParam(defaultValue = "0") Integer from,
                                      @RequestParam(defaultValue = "10") Integer size,
                                      @RequestParam(defaultValue = "NEW") String sort,
                                      @PathVariable String userId) {
        log.info("UserController: Request to find posts, created by user with ID = {}, skip first: {}, " +
                "list size: {}, sorted by creation: {}", userId, from, size, sort);
        return postService.findUserPosts(userId, from, size, sort)
                .stream()
                .map(PostMapper::toDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{postId}")
    public PostDto updatePost(@RequestBody AddPostDto addPostDto,
                              @PathVariable Long postId) {
        log.info("UserController: Request to update the post with ID = {}, new post's data: {}", postId,
                addPostDto.toString());
        return PostMapper.toDto(postService.updatePost(postId, addPostDto));
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePost(@PathVariable Long postId) {
        log.info("UserController: Request to remove post with ID = {}", postId);
        postService.removePost(postId);
    }

    @PutMapping(value = "/{userId}/friends/{friendId}")
    public void addToFriends(@PathVariable Long userId, @PathVariable Long friendId) {
        log.info("UserController: Request to add friend with ID = {} from user with ID = {}", friendId, userId);
        postService.addToFriends(userId, friendId);
    }

    @PatchMapping(value = "/{userId}/friends/{friendId}/")
    public void confirmFriendship(@PathVariable Long userId, @PathVariable Long friendId,
                                  @RequestParam FriendshipStatus friendshipStatus) {
        log.info("UserController: User with ID = {} wants to {} his friendship with user ID = {}",
                friendId, friendshipStatus, userId);
        postService.confirmOrRejectFriendship(userId, friendId, friendshipStatus);
    }

}
