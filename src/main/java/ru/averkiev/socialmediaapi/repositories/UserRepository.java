package ru.averkiev.socialmediaapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.socialmediaapi.models.User;

import java.util.Optional;

/**
 * Интерфейс представляет собой функциональность взаимодействия объекта User с базой данных.
 * @author mrGreenNV
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Выполняет поиск пользователя по имени в базе данных.
     * @param username имя пользователя.
     * @return Optional, содержащий найденного пользователя или пустой.
     */
    Optional<User> findUserByUsername(String username);

    /**
     * Выполняет поиск пользователя по электронной почте в базе данных.
     * @param email электронная почта пользователя.
     * @return Optional, содержащий найденного пользователя или пустой.
     */
    Optional<User> findUserByEmail(String email);
}