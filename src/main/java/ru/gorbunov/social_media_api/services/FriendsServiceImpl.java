package ru.gorbunov.social_media_api.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gorbunov.social_media_api.enums.EventType;
import ru.gorbunov.social_media_api.enums.FriendshipStatus;
import ru.gorbunov.social_media_api.enums.Operation;
import ru.gorbunov.social_media_api.exception.ObjectNotFoundException;
import ru.gorbunov.social_media_api.exception.ValidationException;
import ru.gorbunov.social_media_api.models.Event;
import ru.gorbunov.social_media_api.repositories.EventRepository;
import ru.gorbunov.social_media_api.repositories.FriendsRepository;
import ru.gorbunov.social_media_api.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FriendsServiceImpl implements FriendsService {

    FriendsRepository friendsRepository;

    UserRepository userRepository;
    EventRepository eventRepository;

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
    private void checkFriends(Long userId, Long friendId) {
        if (Objects.equals(userId, friendId)) {
            throw new ValidationException("You cannot add yourself!");
        }
        if (userRepository.findById(friendId).isEmpty()) {
            throw new ObjectNotFoundException(String.format("User with ID = %s was not found", friendId));
        }
    }
}
