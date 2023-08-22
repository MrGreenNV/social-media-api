package ru.averkiev.socialmediaapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.socialmediaapi.models.UserFriend;

/**
 * Интерфейс представляет собой функциональность взаимодействия объекта UserFriend с базой данных.
 * @author mrGreenNV
 */
@Repository
public interface UserFriendRepository extends JpaRepository<UserFriend, Long> {
}
