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
     * Позволяет создать подписку пользователя.
     * @param user пользователь.
     * @param subscribedUser пользователь, на которого осуществляется подписка.
     * @return объект Subscriber, содержащий данный о подписке.
     */
    Subscriber save(User user, User subscribedUser);

    /**
     * Позволяет выполнить поиск подписки.
     * @param user пользователь.
     * @param subscribedUser подписка пользователя.
     * @return объект Subscriber, содержащий данный о подписке.
     * @throws SubscriberNotFoundException выбрасывает, если подписка не найдена.
     */
    Subscriber findByUserAndSubscribedUser(User user, User subscribedUser) throws SubscriberNotFoundException;

    /**
     * Позволяет получить список всех подписок конкретного пользователя.
     * @param user пользователь.
     * @return список объектов Subscriber, содержащих информацию о подписках.
     */
    List<Subscriber> findAllByUser(User user);

    /**
     * Позволяет удалить подписку.
     * @param subscriber удаляемая подписка.
     * @throws SubscriberNotFoundException выбрасывает, если подписка не найдена.
     */
    void delete(Subscriber subscriber) throws SubscriberNotFoundException;

    /**
     * Позволяет удалить подписку.
     * @param user пользователь.
     * @param subscribedUser подписка пользователя.
     * @throws SubscriberNotFoundException выбрасывает, если подписка не найдена.
     */
    void deleteByUserAndSubscribedUser(User user, User subscribedUser) throws SubscriberNotFoundException;

}
