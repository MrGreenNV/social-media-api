package ru.averkiev.socialmediaapi.services;

import ru.averkiev.socialmediaapi.exceptions.SubscriptionNotFoundException;
import ru.averkiev.socialmediaapi.models.Subscription;
import ru.averkiev.socialmediaapi.models.User;

import java.util.List;

/**
 * Интерфейс определяет функциональность для подписчиков пользователей.
 * @author mrGreenNV
 */
public interface SubscriptionService {

    /**
     * Позволяет создать подписку на пользователя.
     * @param user пользователь.
     * @param follower подписчик пользователя.
     * @return объект Subscription, содержащий данный о подписчике.
     */
    Subscription save(User user, User follower);

    /**
     * Позволяет выполнить поиск подписки на пользователя.
     * @param user пользователь.
     * @param follower подписчик пользователя.
     * @return объект Subscription, содержащий данный о подписчике.
     * @throws SubscriptionNotFoundException выбрасывает, если подписчик не найден.
     */
    Subscription findByUserAndSubscription(User user, User follower) throws SubscriptionNotFoundException;

    /**
     * Позволяет получить список всех подписок на конкретного пользователя.
     * @param user пользователь.
     * @return список объектов Subscription, содержащих информацию о подписчиках.
     */
    List<Subscription> findAllByUser(User user);

    /**
     * Позволяет удалить подписку на пользователя.
     * @param subscription удаляемая подписка на пользователя.
     * @throws SubscriptionNotFoundException выбрасывает, если подписчик не найден.
     */
    void delete(Subscription subscription) throws SubscriptionNotFoundException;

    /**
     * Позволяет удалить подписку на пользователя.
     * @param user пользователь.
     * @param follower подписчик пользователя.
     * @throws SubscriptionNotFoundException выбрасывает, если подписчик не найден.
     */
    void deleteByUserAndSubscribedUser(User user, User follower) throws SubscriptionNotFoundException;

}
