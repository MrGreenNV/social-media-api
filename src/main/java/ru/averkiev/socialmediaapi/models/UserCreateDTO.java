package ru.averkiev.socialmediaapi.models;

import lombok.Getter;
import lombok.Setter;
import ru.averkiev.socialmediaapi.validations.CustomEmail;
import ru.averkiev.socialmediaapi.validations.CustomUsername;

/**
 * DTO для передачи регистрационных данных пользователя.
 * @author mrGreenNV
 */
@Getter
@Setter
public class UserCreateDTO {

    /**
     * Имя пользователя в системе.
     */
    @CustomUsername
    private String username;

    /**
     * Хэшированный пароль пользователя.
     */
    private String password;

    /**
     * Электронная почта пользователя.
     */
    @CustomEmail
    private String email;

}

