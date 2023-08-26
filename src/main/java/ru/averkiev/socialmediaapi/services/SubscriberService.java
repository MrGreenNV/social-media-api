package ru.averkiev.socialmediaapi.services;

import ru.averkiev.socialmediaapi.exceptions.SubscriberNotFoundException;
import ru.averkiev.socialmediaapi.models.Subscriber;
import ru.averkiev.socialmediaapi.models.User;

import java.util.List;

/**
 * Интерфейс определяет функциональность для подписок пользователей.
 * @author mrGreenNV
 */
public interface SubscriberService {

    /**
     * Позволяет создать подписчика пользователя.
     * @param user пользователь.
     * @param follower пользователь, который осуществляет подписку.
     * @return объект Subscriber, содержащий данный о подписчике.
     */
    Subscriber save(User user, User follower);

    /**
     * Позволяет выполнить поиск подписчика.
     * @param user пользователь.
     * @param follower подписчик пользователя.
     * @return объект Subscriber, содержащий данный о подписчике.
     * @throws SubscriberNotFoundException выбрасывает, если подписчик не найден.
     */
    Subscriber findByUserAndSubscribedUser(User user, User follower) throws SubscriberNotFoundException;

    /**
     * Позволяет получить список всех подписчиков на конкретного пользователя.
     * @param user пользователь.
     * @return список объектов Subscriber, содержащих информацию о подписчиках.
     */
    List<Subscriber> findAllByUser(User user);

    /**
     * Позволяет удалить подписчика.
     * @param subscriber удаляемый подписчик.
     * @throws SubscriberNotFoundException выбрасывает, если подписчик не найден.
     */
    void delete(Subscriber subscriber) throws SubscriberNotFoundException;

    /**
     * Позволяет удалить подписчика.
     * @param user пользователь.
     * @param follower подписка пользователя.
     * @throws SubscriberNotFoundException выбрасывает, если подписчик не найден.
     */
    void deleteByUserAndSubscribedUser(User user, User follower) throws SubscriberNotFoundException;

}
