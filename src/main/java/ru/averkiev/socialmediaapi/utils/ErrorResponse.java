package ru.averkiev.socialmediaapi.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс обертка для отправки ошибок в ответе.
 * @author mrGreenNV
 */
@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    /** Код ошибки */
    private int errorCode;

    /** Сообщение об ошибке. */
    private String errorMessage;
}
