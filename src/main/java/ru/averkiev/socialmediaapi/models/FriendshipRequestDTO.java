package ru.averkiev.socialmediaapi.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO класса запросов на дружбу для передачи данных.
 * @author mrGreenNV
 */
@Data
@Schema(description = "DTO сущности запроса на дружбу")
public class FriendshipRequestDTO {

    /** Идентификатор пользователя от которого поступил запрос на дружбу. */
    @Schema(description = "Идентификатор пользователя, инициировавшего запрос на дружбу")
    private Long fromUserId;

    /** Идентификатор пользователя, которому поступил запрос на дружбу. */
    @Schema(description = "Идентификатор пользователя, в отношении которого инициирован запрос на дружбу")
    private Long toUserId;
}
