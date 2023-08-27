package ru.averkiev.socialmediaapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.socialmediaapi.models.Subscription;
import ru.averkiev.socialmediaapi.models.User;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс представляет собой функциональность взаимодействия объекта Subscription с базой данных.
 * @author mrGreenNV
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    /**
     * Выполняет поиск подписки в базе данных.
     * @param user пользователь.
     * @param subscriptionUser подписка пользователя.
     * @return Optional содержащий подписку, если она была найдена, иначе - пустой.
     */
    Optional<Subscription> findSubscriptionByUserAndSubscriptionUser(User user, User subscriptionUser);

    /**
     * Выполняет поиск всех подписок пользователя.
     * @param user пользователь.
     * @return список подписок.
     */
    List<Subscription> findAllByUser(User user);
}
