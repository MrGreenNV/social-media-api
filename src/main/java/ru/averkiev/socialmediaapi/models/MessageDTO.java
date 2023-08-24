package ru.averkiev.socialmediaapi.models;

import lombok.Data;

/**
 * DTO для передачи данных о сообщении.
 * @author mrGreenNV
 */
@Data
public class MessageDTO {

    /** Идентификатор сообщения. */
    private Long id;

    /** Идентификатор отправителя. */
    private Long senderId;

    /** Идентификатор получателя. */
    private Long receiverId;

    /** Текст сообщения. */
    private String content;
}
