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
@Schema(description = "DTO дружеской связи пользователей")
public class UserFriendDTO {

    /** Имя пользователя в системе. */
    @Schema(description = "Имя пользователя в системе")
    private String username;


    /** Электронная почта пользователя. */
    @Schema(description = "Электронная почта пользователя")
    private String email;
}
