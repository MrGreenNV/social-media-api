package ru.averkiev.socialmediaapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.socialmediaapi.security.AccessToken;

import java.util.Optional;

/**
 * Интерфейс представляет собой репозиторий access токенов.
 * @author mrGreenNV
 */
@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {
    Optional<AccessToken> findByUserId(long userId);

}
