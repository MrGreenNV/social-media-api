package ru.averkiev.socialmediaapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.socialmediaapi.models.Subscription;

/**
 * Интерфейс представляет собой функциональность взаимодействия объекта Subscription с базой данных.
 * @author mrGreenNV
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
