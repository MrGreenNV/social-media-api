package ru.averkiev.socialmediaapi.services;

import ru.averkiev.socialmediaapi.exceptions.UserFriendNotFoundException;
import ru.averkiev.socialmediaapi.models.User;
import ru.averkiev.socialmediaapi.models.UserFriend;

import java.util.List;

/**
 * Интерфейс определяет функциональность для дружеских связей между пользователями.
 * @author mrGreenNV
 */
public interface UserFriendService {

    /**
     * Позволяет создать дружескую связь между пользователями.
     * @param user пользователь.
     * @param friend друг пользователя.
     * @return объект UserFriend, содержащий данный о связи.
     */
    UserFriend save(User user, User friend);

    /**
     * Позволяет выполнить поиск связи пользователей по самим пользователям.
     * @param user пользователь.
     * @param friend друг пользователя.
     * @return объект UserFriend, содержащий данный о связи.
     * @throws UserFriendNotFoundException выбрасывает, если связь между пользователями не найдена.
     */
    UserFriend findByUserAndFriend(User user, User friend) throws UserFriendNotFoundException;

    /**
     * Позволяет получить список всех дружеских связей для конкретного пользователя.
     * @param user пользователь.
     * @return список объектов UserFriend, содержащих информацию о связях.
     */
    List<UserFriend> findAllByUser(User user);

    /**
     * Позволяет удалить дружескую связь между пользователями.
     * @param userFriend удаляемая связь.
     * @throws UserFriendNotFoundException выбрасывает, если связь между пользователями не найдена.
     */
    void delete(UserFriend userFriend) throws UserFriendNotFoundException;

    /**
     * Позволяет удалить дружескую связь между пользователями.
     * @param user пользователь.
     * @param friend друг пользователя.
     * @throws UserFriendNotFoundException выбрасывает, если связь между пользователями не найдена.
     */
    void deleteByUserAndFriend(User user, User friend) throws UserFriendNotFoundException;

}
