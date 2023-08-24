package ru.averkiev.socialmediaapi.security;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Сущность JWT запроса для аутентификации пользователей")
public class JwtRequest {

    /** Имя пользователя в системе. */
    @Schema(description = "Имя пользователя в системе")
    private String username;

    /** Хэшированный пароль пользователя. */
    @Schema(description = "Пароль пользователя")
    private String password;
}
