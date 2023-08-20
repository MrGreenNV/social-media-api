package ru.averkiev.socialmediaapi.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Класс представляет ответ на запрос аутентификации с использованием JWT.
 * Этот класс используется для передачи информации о токенах доступа и обновления от микросервиса аутентификации
 * и авторизации клиенту в ответ на успешную аутентификацию.
 * Содержит в себе информацию об access и refresh токенах.
 * @author mrGreenNV
 */
@Getter
@AllArgsConstructor
public class JwtResponse {

    /** Начало заголовка содержащим токен. */
    private final String type = "Bearer";

    /** Access токен в строковом представлении. */
    private String accessToken;

    /** Refresh токен в строковом представлении. */
    private String refreshToken;
}
