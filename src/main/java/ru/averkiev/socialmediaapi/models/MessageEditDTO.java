package ru.averkiev.socialmediaapi.models;

import lombok.Data;

/**
 * DTO сообщения для передачи данных об измененном контенте.
 * @author mrGreenNV
 */
@Data
public class MessageEditDTO {

    /** Контент сообщения. */
    private String content;
}
