package ru.averkiev.socialmediaapi.services;

import ru.averkiev.socialmediaapi.exceptions.SubscriptionNotFoundException;
import ru.averkiev.socialmediaapi.models.Subscription;
import ru.averkiev.socialmediaapi.models.User;

import java.util.List;

/**
 * Интерфейс определяет функциональность для подписок пользователей.
 * @author mrGreenNV
 */
public interface SubscriptionService {

    /**
     * Позволяет создать подписку пользователя.
     * @param user пользователь.
     * @param subscriptionUser подписка пользователя.
     * @return объект Subscription, содержащий данные о подписке.
     */
    Subscription save(User user, User subscriptionUser);

    /**
     * Позволяет выполнить поиск подписки пользователя.
     * @param user пользователь.
     * @param subscriptionUser подписка пользователя.
     * @return объект Subscription, содержащий данный о подписке.
     * @throws SubscriptionNotFoundException выбрасывает, если подписка не найдена.
     */
    Subscription findByUserAndSubscription(User user, User subscriptionUser) throws SubscriptionNotFoundException;

    /**
     * Позволяет получить список всех подписок конкретного пользователя.
     * @param user пользователь.
     * @return список объектов Subscription, содержащих информацию о подписках.
     */
    List<Subscription> findAllByUser(User user);

    /**
     * Позволяет удалить подписку пользователя.
     * @param subscription удаляемая подписка пользователя.
     * @throws SubscriptionNotFoundException выбрасывает, если подписка не найдена.
     */
    void delete(Subscription subscription) throws SubscriptionNotFoundException;

    /**
     * Позволяет удалить подписку пользователя.
     * @param user пользователь.
     * @param subscriptionUser подписка пользователя.
     * @throws SubscriptionNotFoundException выбрасывает, если подписка не найден.
     */
    void deleteByUserAndSubscribedUser(User user, User subscriptionUser) throws SubscriptionNotFoundException;

}
