package ru.averkiev.socialmediaapi.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    /** Список ошибок */
    private List<String> errors;

    /**
     * Конструктор позволяет создать объект ErrorResponse с двумя параметрами.
     * @param errorCode код ошибки.
     * @param errorMessage сообщение об ошибке.
     */
    public ErrorResponse(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
