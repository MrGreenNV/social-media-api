package ru.averkiev.socialmediaapi.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.averkiev.socialmediaapi.exceptions.SubscriptionNotFoundException;
import ru.averkiev.socialmediaapi.models.Subscription;
import ru.averkiev.socialmediaapi.models.User;
import ru.averkiev.socialmediaapi.repositories.SubscriptionRepository;
import ru.averkiev.socialmediaapi.services.SubscriptionService;

import java.util.List;

/**
 * Класс реализует функциональность для подписчиков пользователей.
 * @author mrGreenNV
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    /** Репозиторий для обращения к базе данных. */
    private final SubscriptionRepository subscriptionRepository;

    /**
     * Позволяет создать подписчика пользователя.
     * @param user пользователь.
     * @param follower подписчик пользователя.
     * @return объект Subscription, содержащий данный о подписчике.
     */
    @Override
    public Subscription save(User user, User follower) {
        Subscription subscription = new Subscription(user, follower);
        subscription = subscriptionRepository.save(subscription);
        log.info("IN save - подписка пользователя: {} на пользователя: {} успешно сохранена", user.getUsername(), follower.getUsername());
        return subscription;
    }

    /**
     * Позволяет выполнить поиск подписки на пользователя.
     * @param user пользователь.
     * @param follower подписчик пользователя.
     * @return объект Subscription, содержащий данный о подписчике.
     * @throws SubscriptionNotFoundException выбрасывает, если подписчик не найден.
     */
    @Override
    public Subscription findByUserAndSubscription(User user, User follower) throws SubscriptionNotFoundException {
        Subscription subscription = subscriptionRepository.findSubscriptionByUserAndFollower(user, follower).orElse(null);
        if (subscription == null) {
            log.error("IN findByUserAndSubscription - подписчик: {} на пользователя: {} не найден", follower.getUsername(), user.getUsername());
            throw new SubscriptionNotFoundException("Подписчик на пользователя: " + user.getUsername() + " не найден");
        }
        log.info("IN findByUserAndSubscribedUser - подписчик: {} на пользователя: {} успешно найден", follower.getUsername(), user.getUsername());
        return subscription;
    }

    /**
     * Позволяет получить список всех подписок на конкретного пользователя.
     * @param user пользователь.
     * @return список объектов Subscription, содержащих информацию о подписчиках.
     */
    @Override
    public List<Subscription> findAllByUser(User user) {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        log.info("IN findAllByUser - список подписчиков на пользователя: {} успешно получен", user.getUsername());
        return subscriptions;
    }

    /**
     * Позволяет удалить подписку на пользователя.
     * @param subscription удаляемая подписка на пользователя.
     * @throws SubscriptionNotFoundException выбрасывает, если подписчик не найден.
     */
    @Override
    public void delete(Subscription subscription) throws SubscriptionNotFoundException {

        if (!subscriptionRepository.existsById(subscription.getId())) {
            log.error("IN delete - подписка на пользователя подписчика: {} не удалена", subscription.getFollower().getUsername());
            throw new SubscriptionNotFoundException("Подписка на пользователя: " + subscription.getUser().getUsername() + " не найдена");
        }

        subscriptionRepository.delete(subscription);
        log.info("IN delete - подписка на пользователя подписчика: {} успешно удалена", subscription.getFollower().getUsername());
    }

    /**
     * Позволяет удалить подписку на пользователя.
     * @param user пользователь.
     * @param follower подписчик пользователя.
     * @throws SubscriptionNotFoundException выбрасывает, если подписчик не найден.
     */
    @Override
    public void deleteByUserAndSubscribedUser(User user, User follower) throws SubscriptionNotFoundException {
        Subscription subscription = findByUserAndSubscription(user, follower);
        delete(subscription);
    }
}
