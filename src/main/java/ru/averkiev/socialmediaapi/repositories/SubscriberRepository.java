package ru.averkiev.socialmediaapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.socialmediaapi.models.Subscriber;
import ru.averkiev.socialmediaapi.models.User;

import java.util.Optional;

/**
 * Интерфейс представляет собой функциональность взаимодействия объекта Subscriber с базой данных.
 * @author mrGreenNV
 */
@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

    /**
     * Выполняет поиск подписчика в базе данных.
     * @param user пользователь.
     * @param follower подписчик пользователя.
     * @return Optional содержащий подписчика, если она была найдена, иначе - пустой.
     */
    Optional<Subscriber> findSubscriberByUserAndFollower(User user, User follower);

}
