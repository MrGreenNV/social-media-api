package ru.averkiev.socialmediaapi.models;

import lombok.Data;

/**
 * DTO для передачи информации об имени и идентификаторе пользователей.
 * @author mrGreenNV
 */
@Data
public class UserDTO {

    /** Идентификатор пользователя. */
    private Long id;

    /** Имя пользователя. */
    private String username;
}
