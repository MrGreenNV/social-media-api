package ru.averkiev.socialmediaapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.averkiev.socialmediaapi.security.RefreshToken;

import java.util.Optional;

/**
 * Интерфейс представляет собой репозиторий refresh токенов.
 * @author mrGreenNV
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUserId(long userId);
}
