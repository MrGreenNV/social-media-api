package ru.averkiev.socialmediaapi.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO для передачи данных о сообщении.
 * @author mrGreenNV
 */
@Data
@Schema(description = "DTO сущности сообщения")
public class MessageDTO {

    /** Идентификатор сообщения. */
    @Schema(description = "Идентификатор сообщения")
    private Long id;

    /** Идентификатор отправителя. */
    @Schema(description = "Идентификатор отправителя сообщения")
    private Long senderId;

    /** Идентификатор получателя. */
    @Schema(description = "Идентификатор получателя сообщения")
    private Long receiverId;

    /** Текст сообщения. */
    @Schema(description = "Контент сообщения")
    private String content;
}
