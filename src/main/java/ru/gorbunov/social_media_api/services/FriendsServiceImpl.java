package ru.gorbunov.social_media_api.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public void confirmFriendship(Long userId, Long friendId, String friendshipStatus) {
        checkFriends(userId, friendId);
        friendsRepository.confirmOrRejectFriendship(userId, friendId, friendshipStatus);
        eventRepository.save(new Event(null, LocalDateTime.now(), userId, EventType.FRIEND,
                Operation.CONFIRM, friendId));
    }

    @Override
    public void rejectFriendship(Long userId, Long friendId, String friendshipStatus) {
        friendsRepository.confirmOrRejectFriendship(userId, friendId, friendshipStatus);
        eventRepository.save(new Event(null, LocalDateTime.now(), userId, EventType.FRIEND,
                Operation.REJECT, friendId));
        log.info("User with ID = {} {} friendship with user ID = {}", userId, friendshipStatus, friendId);
    }

    @Override
    public void checkFriendshipResponse(Long userId, Long friendId, String response) {
        if (response.equals("YES")) {
            FriendshipStatus friendshipStatus = FriendshipStatus.CONFIRMED;
            log.info("FriendshipService: User with ID = {} wants to {} his friendship with user ID = {}",
                    friendId, friendshipStatus, userId);
            confirmFriendship(userId, friendId, friendshipStatus.toString());
            addToFriends(friendId, userId);
            confirmFriendship(friendId, userId, friendshipStatus.toString());
        }
        else if (response.equals("NO")) {
            FriendshipStatus friendshipStatus = FriendshipStatus.REJECTED;
            log.info("FriendshipService: User with ID = {} wants to {} his friendship with user ID = {}",
                    friendId, friendshipStatus, userId);
            rejectFriendship(userId, friendId, friendshipStatus.toString());
        }
        else {
            throw new IllegalArgumentException("Invalid response parameter");
        }
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
