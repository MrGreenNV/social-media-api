package ru.averkiev.socialmediaapi.models;

import lombok.Getter;
import lombok.Setter;
import ru.averkiev.socialmediaapi.validations.CustomEmail;
import ru.averkiev.socialmediaapi.validations.CustomUsername;

/**
 * DTO 
 * @author mrGreenNV
 */
@Getter
@Setter
public class UserRegistrationDTO {

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
