package ru.averkiev.socialmediaapi.security;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Сущность JWT ответа для аутентификации пользователей")
public class JwtResponse {

    /** Начало заголовка содержащим токен. */
    @Schema(description = "Начало заголовка содержащим токен")
    private final String type = "Bearer";

    /** Access токен в строковом представлении. */
    @Schema(description = "Access токен")
    private String accessToken;

    /** Refresh токен в строковом представлении. */
    @Schema(description = "Refresh токен")
    private String refreshToken;
}
