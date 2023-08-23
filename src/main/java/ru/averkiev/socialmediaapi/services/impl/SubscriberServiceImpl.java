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
 * Класс реализует функциональность для подписок пользователей.
 * @author mrGreenNV
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriberServiceImpl implements SubscriberService {

    /** Репозиторий для обращения к базе данных. */
    private final SubscriberRepository subscriberRepository;

    /**
     * Позволяет создать подписку пользователя.
     * @param user пользователь.
     * @param subscribedUser пользователь, на которого осуществляется подписка.
     * @return объект Subscriber, содержащий данный о подписке.
     */
    @Override
    public Subscriber save(User user, User subscribedUser) {
        Subscriber subscriber = new Subscriber(user, subscribedUser);
        subscriber = subscriberRepository.save(subscriber);
        log.info("IN save - подписка пользователя: {} на пользователя: {} успешно сохранена", user.getUsername(), subscribedUser.getUsername());
        return subscriber;
    }

    /**
     * Позволяет выполнить поиск подписки.
     * @param user пользователь.
     * @param subscribedUser подписка пользователя.
     * @return объект Subscriber, содержащий данный о подписке.
     * @throws SubscriberNotFoundException выбрасывает, если подписка не найдена.
     */
    @Override
    public Subscriber findByUserAndSubscribedUser(User user, User subscribedUser) throws SubscriberNotFoundException {
        Subscriber subscriber = subscriberRepository.findSubscriberByUserAndSubscribedUser(user, subscribedUser).orElse(null);
        if (subscriber == null) {
            log.error("IN findByUserAndSubscribedUser - подписка пользователя: {} на пользователя: {} не найдена", user.getUsername(), subscribedUser.getUsername());
            throw new SubscriberNotFoundException("Подписка на пользователя: " + user.getUsername() + " не найдена");
        }
        log.info("IN findByUserAndSubscribedUser - подписка пользователя: {} на пользователя: {} успешно найдена", user.getUsername(), subscribedUser.getUsername() );
        return subscriber;
    }

    /**
     * Позволяет получить список всех подписок конкретного пользователя.
     * @param user пользователь.
     * @return список объектов Subscriber, содержащих информацию о подписках.
     */
    @Override
    public List<Subscriber> findAllByUser(User user) {
        List<Subscriber> subscribers = subscriberRepository.findAll();
        log.info("IN findAllByUser - список подписок пользователя: {} успешно получен", user.getUsername());
        return subscribers;
    }

    /**
     * Позволяет удалить подписку.
     * @param subscriber удаляемая подписка.
     * @throws SubscriberNotFoundException выбрасывает, если подписка не найдена.
     */
    @Override
    public void delete(Subscriber subscriber) throws SubscriberNotFoundException {

        if (!subscriberRepository.existsById(subscriber.getId())) {
            log.error("IN delete - подписка на пользователя: {} не удалена", subscriber.getSubscribedUser().getUsername());
            throw new SubscriberNotFoundException("Подписка на пользователя: " + subscriber.getSubscribedUser().getUsername() + " не найдена");
        }

        subscriberRepository.delete(subscriber);
        log.info("IN delete - подписка на пользователя: {} успешно удалена", subscriber.getSubscribedUser().getUsername());

    }

    /**
     * Позволяет удалить подписку.
     * @param user пользователь.
     * @param subscribedUser подписка пользователя.
     * @throws SubscriberNotFoundException выбрасывает, если подписка не найдена.
     */
    @Override
    public void deleteByUserAndSubscribedUser(User user, User subscribedUser) throws SubscriberNotFoundException {
        Subscriber subscriber = findByUserAndSubscribedUser(user, subscribedUser);
        delete(subscriber);
    }
}
