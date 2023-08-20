package ru.averkiev.socialmediaapi.models;

import lombok.Getter;
import lombok.Setter;
import ru.averkiev.socialmediaapi.validations.CustomUsername;

/**
 * Класс для передачи данных о пользователе для входа в систему.
 * @author mrGreenNV
 */
@Getter
@Setter
public class UserLoginDTO {

    /** Имя пользователя в системе. */
    @CustomUsername
    private String username;

    /** Хэшированный пароль пользователя. */
    private String password;

    /** Хэшированное подтверждение пароля пользователя. */
    private String confirmPassword;

}
