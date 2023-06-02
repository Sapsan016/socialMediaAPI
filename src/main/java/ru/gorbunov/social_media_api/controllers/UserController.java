package ru.gorbunov.social_media_api.controllers;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.gorbunov.social_media_api.dto.AddPostDto;
import ru.gorbunov.social_media_api.dto.PostDto;
import ru.gorbunov.social_media_api.mappers.PostMapper;
import ru.gorbunov.social_media_api.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional
public class UserController {


    UserService userService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{userId}")
    public PostDto addPost(@RequestBody @Valid AddPostDto postAddDto, @PathVariable Long userId) {
        log.info("UserController: Request to add a new post: {} by user with ID = {}", postAddDto.toString(), userId);
        return PostMapper.toDto(userService.addNewPost(postAddDto, userId));
    }

    @GetMapping("/{postId}")
    public PostDto getPostById(@PathVariable Long postId) {
        log.info("UserController: Request to find a post wit ID = {}", postId);
        return PostMapper.toDto(userService.findPostById(postId));
    }


    @GetMapping("/{userId}")
    public List<PostDto> getUserPosts(@RequestParam(defaultValue = "0") Integer from,
                                      @RequestParam(defaultValue = "10") Integer size,
                                      @RequestParam(defaultValue = "NEW") String sort,
                                      @PathVariable String userId) {
        log.info("UserController: Request to find posts, created by user with ID = {}, skip first: {}, " +
                "list size: {}, sorted by creation: {}", userId, from, size, sort);
        return userService.findUserPosts(userId, from, size, sort)
                .stream()
                .map(PostMapper::toDto)
                .collect(Collectors.toList());
    }

    @PatchMapping("/{postId}")
    public PostDto updatePost(@RequestBody AddPostDto addPostDto,
                              @PathVariable Long postId) {
        log.info("UserController: Request to update the post with ID = {}, new post's data: {}", postId,
                addPostDto.toString());
        return PostMapper.toDto(userService.updatePost(postId, addPostDto));
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removePost(@PathVariable Long postId) {
        log.info("UserController: Request to remove post with ID = {}", postId);
        userService.removePost(postId);
    }


}
