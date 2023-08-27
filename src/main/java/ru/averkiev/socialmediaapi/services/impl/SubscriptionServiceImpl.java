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
 * Класс реализует функциональность для подписки пользователей.
 * @author mrGreenNV
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    /** Репозиторий для обращения к базе данных. */
    private final SubscriptionRepository subscriptionRepository;

    /**
     * Позволяет создать подписку пользователя.
     * @param user пользователь.
     * @param subscriptionUser подписка пользователя.
     * @return объект Subscription, содержащий данные о подписке.
     */
    @Override
    public Subscription save(User user, User subscriptionUser) {
        Subscription subscription = new Subscription(user, subscriptionUser);
        subscription = subscriptionRepository.save(subscription);
        log.info("IN save - подписка пользователя: {} на пользователя: {} успешно сохранена", user.getUsername(), subscriptionUser.getUsername());
        return subscription;
    }

    /**
     * Позволяет выполнить поиск подписки пользователя.
     * @param user пользователь.
     * @param subscriptionUser подписка пользователя.
     * @return объект Subscription, содержащий данный о подписке.
     * @throws SubscriptionNotFoundException выбрасывает, если подписка не найдена.
     */
    @Override
    public Subscription findByUserAndSubscription(User user, User subscriptionUser) throws SubscriptionNotFoundException {
        Subscription subscription = subscriptionRepository.findSubscriptionByUserAndSubscriptionUser(user, subscriptionUser).orElse(null);
        if (subscription == null) {
            log.error("IN findByUserAndSubscription - подписка пользователя: {} на пользователя: {} не найдена", user.getUsername(), subscriptionUser.getUsername());
            throw new SubscriptionNotFoundException("Подписка пользователя: " + user.getUsername() + " не найдена");
        }
        log.info("IN findByUserAndSubscribedUser - подписка пользователя: {} на пользователя: {} успешно найдена", user.getUsername(), subscriptionUser.getUsername());
        return subscription;
    }

    /**
     * Позволяет получить список всех подписок конкретного пользователя.
     * @param user пользователь.
     * @return список объектов Subscription, содержащих информацию о подписках.
     */
    @Override
    public List<Subscription> findAllByUser(User user) {
        List<Subscription> subscriptions = subscriptionRepository.findAllByUser(user);
        log.info("IN findAllByUser - список подписок пользователя: {} успешно получен", user.getUsername());
        return subscriptions;
    }

    /**
     * Позволяет удалить подписку пользователя.
     * @param subscription удаляемая подписка пользователя.
     * @throws SubscriptionNotFoundException выбрасывает, если подписка не найдена.
     */
    @Override
    public void delete(Subscription subscription) throws SubscriptionNotFoundException {

        if (!subscriptionRepository.existsById(subscription.getId())) {
            log.error("IN delete - подписка пользователя: {} на подписчика: {} не удалена", subscription.getSubscriptionUser().getUsername(), subscription.getUser().getUsername());
            throw new SubscriptionNotFoundException("Подписка на пользователя: " + subscription.getUser().getUsername() + " не найдена");
        }

        subscriptionRepository.delete(subscription);
        log.info("IN delete - подписка пользователя: {} на подписчика: {} успешно удалена", subscription.getSubscriptionUser().getUsername(), subscription.getUser().getUsername());
    }

    /**
     * Позволяет удалить подписку пользователя.
     * @param user пользователь.
     * @param subscriptionUser подписка пользователя.
     * @throws SubscriptionNotFoundException выбрасывает, если подписка не найден.
     */
    @Override
    public void deleteByUserAndSubscribedUser(User user, User subscriptionUser) throws SubscriptionNotFoundException {
        Subscription subscription = findByUserAndSubscription(user, subscriptionUser);
        delete(subscription);
    }
}
