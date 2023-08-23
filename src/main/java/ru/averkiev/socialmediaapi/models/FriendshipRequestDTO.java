package ru.averkiev.socialmediaapi.models;

import lombok.Data;

/**
 * DTO класса запросов на дружбу для передачи данных.
 * @author mrGreenNV
 */
@Data
public class FriendshipRequestDTO {

    /** Идентификатор пользователя от которого поступил запрос на дружбу. */
    private Long fromUser;

    /** Идентификатор пользователя, которому поступил запрос на дружбу. */
    private Long toUser;
}
