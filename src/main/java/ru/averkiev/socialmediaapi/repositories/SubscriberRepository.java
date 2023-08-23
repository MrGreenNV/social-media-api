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
     * Выполняет поиск подписки в базе данных.
     * @param user пользователь.
     * @param subscribedUser подписка пользователя.
     * @return Optional содержащий подписку, если она была найдена, иначе - пустой.
     */
    Optional<Subscriber> findSubscriberByUserAndSubscribedUser(User user, User subscribedUser);

    /**
     * Выполняет проверку существования записи о подписке в базе данных.
     * @param subscriber подписка
     * @return true, если удалось найти запись, иначе - false.
     */
    boolean exists(Subscriber subscriber);
}
