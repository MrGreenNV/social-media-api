package ru.averkiev.socialmediaapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.socialmediaapi.models.Subscription;
import ru.averkiev.socialmediaapi.models.User;

import java.util.Optional;

/**
 * Интерфейс представляет собой функциональность взаимодействия объекта Subscription с базой данных.
 * @author mrGreenNV
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    /**
     * Выполняет поиск подписчика в базе данных.
     * @param user пользователь.
     * @param follower подписчик пользователя.
     * @return Optional содержащий подписчика, если он был найден, иначе - пустой.
     */
    Optional<Subscription> findSubscriptionByUserAndFollower(User user, User follower);

}
