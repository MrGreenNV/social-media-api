package ru.averkiev.socialmediaapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.averkiev.socialmediaapi.security.AccessToken;

import java.util.Optional;

/**
 * @author mrGreenNV
 */
public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {
    Optional<AccessToken> findByUserId(long userId);

}
