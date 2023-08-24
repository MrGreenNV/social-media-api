package ru.averkiev.socialmediaapi.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO сообщения для передачи данных об измененном контенте.
 * @author mrGreenNV
 */
@Data
@Schema(description = "DTO сущности отредактированного сообщения")
public class MessageEditDTO {

    /** Контент сообщения. */
    @Schema(description = "Отредактированный контент сообщения")
    private String content;
}
