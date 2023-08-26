package ru.averkiev.socialmediaapi.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.averkiev.socialmediaapi.exceptions.SubscriberNotFoundException;
import ru.averkiev.socialmediaapi.models.Subscriber;
import ru.averkiev.socialmediaapi.models.User;
import ru.averkiev.socialmediaapi.repositories.SubscriberRepository;
import ru.averkiev.socialmediaapi.services.SubscriberService;

import java.util.List;

/**
 * Класс реализует функциональность для подписчиков пользователей.
 * @author mrGreenNV
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriberServiceImpl implements SubscriberService {

    /** Репозиторий для обращения к базе данных. */
    private final SubscriberRepository subscriberRepository;

    /**
     * Позволяет создать подписчика пользователя.
     * @param user пользователь.
     * @param follower пользователь, который осуществляет подписку.
     * @return объект Subscriber, содержащий данный о подписчике.
     */
    @Override
    public Subscriber save(User user, User follower) {
        Subscriber subscriber = new Subscriber(user, follower);
        subscriber = subscriberRepository.save(subscriber);
        log.info("IN save - подписчик: {} на пользователя: {} успешно создан", follower.getUsername(), user.getUsername());
        return subscriber;
    }

    /**
     * Позволяет выполнить поиск подписчика.
     * @param user пользователь.
     * @param follower подписчик пользователя.
     * @return объект Subscriber, содержащий данный о подписчике.
     * @throws SubscriberNotFoundException выбрасывает, если подписчик не найден.
     */
    @Override
    public Subscriber findByUserAndSubscribedUser(User user, User follower) throws SubscriberNotFoundException {
        Subscriber subscriber = subscriberRepository.findSubscriberByUserAndFollower(user, follower).orElse(null);
        if (subscriber == null) {
            log.error("IN findByUserAndSubscribedUser - подписчик: {} на пользователя: {} успешно создан", follower.getUsername(), user.getUsername());
            throw new SubscriberNotFoundException("Подписчик: " + follower.getUsername() + " пользователя: " + user.getUsername() + " не найден");
        }
        log.info("IN findByUserAndSubscribedUser - подписчик: {} на пользователя: {} успешно создан", follower.getUsername(), user.getUsername());
        return subscriber;
    }

    /**
     * Позволяет получить список всех подписчиков на конкретного пользователя.
     * @param user пользователь.
     * @return список объектов Subscriber, содержащих информацию о подписчиках.
     */
    @Override
    public List<Subscriber> findAllByUser(User user) {
        List<Subscriber> subscribers = subscriberRepository.findAll();
        log.info("IN findAllByUser - список подписчиков пользователя: {} успешно получен", user.getUsername());
        return subscribers;
    }

    /**
     * Позволяет удалить подписчика.
     * @param subscriber удаляемый подписчик.
     * @throws SubscriberNotFoundException выбрасывает, если подписчик не найден.
     */
    @Override
    public void delete(Subscriber subscriber) throws SubscriberNotFoundException {

        if (!subscriberRepository.existsById(subscriber.getId())) {
            log.error("IN delete - подписчик: {} пользователя: {} не удален", subscriber.getFollower().getUsername(), subscriber.getUser().getUsername());
            throw new SubscriberNotFoundException("Подписчик: " + subscriber.getFollower().getUsername() + " пользователя: " + subscriber.getUser().getUsername() + " не найден");
        }

        subscriberRepository.delete(subscriber);
        log.info("IN delete - подписчик: {} пользователя: {} успешно удалена", subscriber.getFollower().getUsername(), subscriber.getUser().getUsername());

    }

    /**
     * Позволяет удалить подписчика.
     * @param user пользователь.
     * @param follower подписка пользователя.
     * @throws SubscriberNotFoundException выбрасывает, если подписчик не найден.
     */
    @Override
    public void deleteByUserAndSubscribedUser(User user, User follower) throws SubscriberNotFoundException {
        Subscriber subscriber = findByUserAndSubscribedUser(user, follower);
        delete(subscriber);
    }
}
