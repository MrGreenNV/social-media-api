package ru.averkiev.socialmediaapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.socialmediaapi.models.Subscriber;

/**
 * Интерфейс представляет собой функциональность взаимодействия объекта Subscriber с базой данных.
 * @author mrGreenNV
 */
@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
}
