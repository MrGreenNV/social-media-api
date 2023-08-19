package ru.averkiev.socialmediaapi.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс представляет запрос на аутентификацию с использованием JWT.
 * Этот класс используется для передачи данных аутентификации (логина и пароля) от клиента.
 * @author mrGreenNV
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest {
    private String login;
    private String password;
}
