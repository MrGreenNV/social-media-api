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
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
}