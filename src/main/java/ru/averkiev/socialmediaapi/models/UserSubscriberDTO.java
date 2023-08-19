package ru.averkiev.socialmediaapi.models;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO для передачи данных о подписчиках.
 * @author mrGreenNV
 */
@Getter
@Setter
public class UserSubscriberDTO {

    /**
     * Имя пользователя в системе.
     */
    private String username;
}
