package ru.averkiev.socialmediaapi.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO для передачи данных о друзьях.
 * @author mrGreenNV
 */
@Getter
@Setter
@Schema(description = "Сущность пользователя с данными о пользователе")
public class UserFriendDTO {

    /** Имя пользователя в системе. */
    @Schema(description = "Имя пользователя в системе", example = "new_user2023")
    private String username;

    /** Электронная почта пользователя. */
    @Schema(description = "Электронная почта пользователя", example = "new_user2023@gamil.com")
    private String email;
}
