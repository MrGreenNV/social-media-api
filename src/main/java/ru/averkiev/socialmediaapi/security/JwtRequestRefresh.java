package ru.averkiev.socialmediaapi.security;

import lombok.Getter;
import lombok.Setter;

/**
 * Класс представляет собой модель для передачи в запросе JWT refresh токена.
 * @author mrGreenNV
 */
@Getter
@Setter
public class JwtRequestRefresh {

    /** Refresh токен в строковом представлении. */
    private String refreshToken;
}
