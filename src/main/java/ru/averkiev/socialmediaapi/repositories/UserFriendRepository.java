package ru.averkiev.socialmediaapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.socialmediaapi.models.User;
import ru.averkiev.socialmediaapi.models.UserFriend;

import java.util.Optional;

/**
 * Интерфейс представляет собой функциональность взаимодействия объекта UserFriend с базой данных.
 * @author mrGreenNV
 */
@Repository
public interface UserFriendRepository extends JpaRepository<UserFriend, Long> {

    /**
     * Выполняет поиск дружеской связи между пользователями в базе данных.
     * @param user пользователь.
     * @param friend друг пользователя.
     * @return Optional содержащий дружескую связь, если она была найдена, иначе - пустой.
     */
    Optional<UserFriend> findUserFriendByUserAndFriend(User user, User friend);

}
