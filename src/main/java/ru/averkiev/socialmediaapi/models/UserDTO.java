package ru.averkiev.socialmediaapi.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO для передачи информации об имени и идентификаторе пользователей.
 * @author mrGreenNV
 */
@Data
@Schema(description = "DTO пользователя")
public class UserDTO {

    /** Идентификатор пользователя. */
    @Schema(description = "Идентификатор пользователя")
    private Long id;

    /** Имя пользователя. */
    @Schema(description = "Имя пользователя в системе")
    private String username;
}
