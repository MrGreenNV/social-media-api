package ru.averkiev.socialmediaapi.models;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Сущность пользователя с данными для регистрации в системе")
public class UserCreateDTO {

    /** Имя пользователя в системе. */
    @Schema(description = "Имя пользователя в системе", example = "new_user2023")
    @CustomUsername
    private String username;

    /** Хэшированный пароль пользователя. */
    @Schema(description = "Пароль пользователя")
    private String password;

    /** Электронная почта пользователя. */
    @Schema(description = "Электронная почта пользователя", example = "new_user2023@gamil.com")
    @CustomEmail
    private String email;

}

