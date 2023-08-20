package ru.averkiev.socialmediaapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.averkiev.socialmediaapi.exceptions.AuthException;
import ru.averkiev.socialmediaapi.security.JwtRequest;
import ru.averkiev.socialmediaapi.security.JwtResponse;
import ru.averkiev.socialmediaapi.services.impl.AuthServiceImpl;
import ru.averkiev.socialmediaapi.utils.ErrorResponse;

/**
 * Класс представляет собой REST-контроллер для аутентификации и авторизации пользователей в системе.
 * Этот класс предоставляет API-endpoints для выполнения операций входа в систему, получения новых и обновления JWT
 * токенов. Все запросы выполняются в формате JSON.
 * @author mrGreenNV
 */
@RestController
@RequestMapping("social-media-api/auth")
@RequiredArgsConstructor
@Tag(
        name = "AuthController",
        description = "Позволяет пользователям пройти аутентификацию"
)
public class AuthController {

    /** Сервис для аутентификации и авторизации пользователей в системе. */
    private final AuthServiceImpl authService;

    /**
     * API-endpoint для выполнения операции входа в систему.
     * @param jwtRequest POST запрос с объектом JwtRequest, содержащим логин и пароль пользователя.
     * @return ResponseEntity с объектом JwtResponse, содержащим access и refresh токены.
     */
    @PostMapping("login")
    @Operation(
            summary = "Вход пользователя в систему",
            description = "Позволяет пройти аутентификацию зарегистрированным пользователям и получить JWT access и refresh токены с помощью имени пользователя и пароля"
    )
    public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest) {
        try {
            final JwtResponse tokens = authService.login(jwtRequest);
            return ResponseEntity.ok(tokens);
        } catch (AuthException aEx) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), aEx.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
