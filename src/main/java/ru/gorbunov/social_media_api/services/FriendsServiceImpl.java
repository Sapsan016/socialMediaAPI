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
import ru.gorbunov.social_media_api.models.User;
import ru.gorbunov.social_media_api.repositories.EventRepository;
import ru.gorbunov.social_media_api.repositories.FriendsRepository;
import ru.gorbunov.social_media_api.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        eventRepository.save(new Event(null, LocalDateTime.now(), getUserById(userId), EventType.FRIEND,
                Operation.ADD, friendId));
        log.info("User with ID = {} added friend with ID = {}", userId, friendId);
    }

    @Override
    public void confirmFriendship(Long userId, Long friendId, String friendshipStatus) {
        checkFriends(userId, friendId);
        friendsRepository.confirmOrRejectFriendship(userId, friendId, friendshipStatus);
        eventRepository.save(new Event(null, LocalDateTime.now(), getUserById(userId), EventType.FRIEND,
                Operation.CONFIRM, friendId));
    }

    @Override
    public void rejectFriendship(Long userId, Long friendId, String friendshipStatus) {
        friendsRepository.confirmOrRejectFriendship(userId, friendId, friendshipStatus);
        eventRepository.save(new Event(null, LocalDateTime.now(), getUserById(userId), EventType.FRIEND,
                Operation.REJECT, friendId));
        log.info("FriendsService: User with ID = {} {} friendship with user ID = {}", userId, friendshipStatus, friendId);
    }

    @Override
    public void checkFriendshipResponse(Long userId, Long friendId, String response) {
        if (response.equals("YES")) {
            FriendshipStatus friendshipStatus = FriendshipStatus.CONFIRMED;
            confirmFriendship(userId, friendId, friendshipStatus.toString());
            addToFriends(friendId, userId);
            confirmFriendship(friendId, userId, friendshipStatus.toString());
            log.info("FriendshipService: User with ID = {} and with user ID = {} are friends now",
                    friendId, userId);
        } else if (response.equals("NO")) {
            FriendshipStatus friendshipStatus = FriendshipStatus.REJECTED;
            rejectFriendship(userId, friendId, friendshipStatus.toString());
            log.info("FriendshipService: User with ID = {} {} his friendship with user ID = {}",
                    friendId, friendshipStatus, userId);
        } else {
            throw new ValidationException("Invalid response parameter");
        }
    }

    @Override
    public List<Event> getUserFeed(Long userId, int from, int size) {
        List<Long> friendsIds = friendsRepository.getFriendsIds(userId);
        List<Event> events = new ArrayList<>();
        for (Long id : friendsIds) {
            List<Event> userEvents = eventRepository.findAllByUserId(id);
            events.addAll(userEvents);
        }
        return events.stream()
                .sorted(Comparator.comparing(Event::getTimestamp).reversed())
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public void cancelFriendship(Long userId, Long friendId) {
        System.out.println(friendsRepository.isFriends(userId));

        if (friendsRepository.isCanceled(userId)) {
            throw new ValidationException(String.format("Users with ID = %d has canceled his friendship with user ID = %d",
                    userId, friendId));
        }
        friendsRepository.confirmOrRejectFriendship(userId, friendId, FriendshipStatus.CANCELED.toString());
        eventRepository.save(new Event(null, LocalDateTime.now(), getUserById(userId), EventType.FRIEND,
                Operation.CANCEL, friendId));
        log.info("FriendsService: User with ID = {} canceled friendship with user ID = {}", userId, friendId);

    }

    private void checkFriends(Long userId, Long friendId) {
        if (Objects.equals(userId, friendId)) {
            throw new ValidationException("You cannot add yourself as a friend!");
        }
        if (userRepository.findById(friendId).isEmpty()) {
            throw new ObjectNotFoundException(String.format("User with ID = %d was not found", friendId));
        }
        if (friendsRepository.isFriends(userId)) {
            throw new ValidationException(String.format("Users with ID = %d and ID = %d are friends already",
                    userId, friendId));
        }
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new ObjectNotFoundException(String.format("User with ID = %s was not found", userId)));
    }

}
