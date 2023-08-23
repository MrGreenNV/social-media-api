package ru.averkiev.socialmediaapi.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.averkiev.socialmediaapi.exceptions.UserFriendNotFoundException;
import ru.averkiev.socialmediaapi.models.User;
import ru.averkiev.socialmediaapi.models.UserFriend;
import ru.averkiev.socialmediaapi.repositories.UserFriendRepository;
import ru.averkiev.socialmediaapi.services.UserFriendService;

import java.util.List;

/**
 * Класс реализует функциональность для дружеских связей пользователей.
 * @author mrGreenNV
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserFriendServiceImpl implements UserFriendService {

    /** Репозиторий для обращения к базе данных. */
    private final UserFriendRepository userFriendRepository;

    /**
     * Позволяет создать дружескую связь между пользователями.
     * @param user пользователь.
     * @param friend друг пользователя.
     * @return объект UserFriend, содержащий данный о связи.
     */
    @Override
    public UserFriend save(User user, User friend) {
        UserFriend userFriend = new UserFriend(user, friend);
        userFriend = userFriendRepository.save(userFriend);
        log.info("IN save - дружеская связь пользователя: {} на пользователя: {} успешно сохранена", user.getUsername(), friend.getUsername());
        return userFriend;
    }

    /**
     * Позволяет выполнить поиск связи пользователей по самим пользователям.
     * @param user пользователь.
     * @param friend друг пользователя.
     * @return объект UserFriend, содержащий данный о связи.
     * @throws UserFriendNotFoundException выбрасывает, если связь между пользователями не найдена.
     */
    @Override
    public UserFriend findByUserAndFriend(User user, User friend) throws UserFriendNotFoundException {
        UserFriend userFriend = userFriendRepository.findUserFriendByUserAndFriend(user, friend).orElse(null);
        if (userFriend == null) {
            log.error("IN findByUserAndFriend - связь пользователя: {} с пользователем: {} не найдена", user.getUsername(), friend.getUsername());
            throw new UserFriendNotFoundException("Дружеская связь пользователей: " + user.getUsername() + " и " + friend.getUsername() + " не найдена.");
        }
        log.info("IN findByUserAndFriend - связь пользователя: {} с пользователем: {} успешно найдена", user.getUsername(), friend.getUsername());
        return userFriend;
    }

    /**
     * Позволяет получить список всех дружеских связей для конкретного пользователя.
     * @param user пользователь.
     * @return список объектов UserFriend, содержащих информацию о связях.
     */
    @Override
    public List<UserFriend> findAllByUser(User user) {
        List<UserFriend> userFriends = userFriendRepository.findAll();
        log.info("IN findAllByUser - список дружеских связей пользователя: {} успешно получен", user.getUsername());
        return userFriends;
    }

    /**
     * Позволяет удалить дружескую связь между пользователями.
     * @param userFriend удаляемая связь.
     * @throws UserFriendNotFoundException выбрасывает, если связь между пользователями не найдена.
     */
    @Override
    public void delete(UserFriend userFriend) throws UserFriendNotFoundException {

        if (!userFriendRepository.existsById(userFriend.getId())) {
            log.error("IN findByUserAndFriend - связь пользователя: {} с пользователем: {} не удалена", userFriend.getUser().getUsername(), userFriend.getFriend().getUsername());
            throw new UserFriendNotFoundException("Дружеская связь пользователей: " + userFriend.getUser().getUsername() + " и " + userFriend.getFriend().getUsername() + " не найдена.");
        }

        userFriendRepository.delete(userFriend);
        log.info("IN findByUserAndFriend - связь пользователя: {} с пользователем: {} успешно удалена", userFriend.getUser().getUsername(), userFriend.getFriend().getUsername());
    }

    /**
     * Позволяет удалить дружескую связь между пользователями.
     * @param user пользователь.
     * @param friend друг пользователя.
     * @throws UserFriendNotFoundException выбрасывает, если связь между пользователями не найдена.
     */
    @Override
    public void deleteByUserAndFriend(User user, User friend) throws UserFriendNotFoundException {
        UserFriend userFriend = findByUserAndFriend(user, friend);
        System.out.println("ID: " + userFriend.getId());
        delete(userFriend);
    }
}
