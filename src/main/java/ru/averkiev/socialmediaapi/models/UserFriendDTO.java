package ru.averkiev.socialmediaapi.models;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO для передачи данных о друзьях.
 * @author mrGreenNV
 */
@Getter
@Setter
public class UserFriendDTO {

    /**
     * Имя пользователя в системе.
     */
    private String username;

    /**
     * Электронная почта пользователя.
     */
    private String email;
}
