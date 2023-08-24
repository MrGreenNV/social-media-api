package ru.averkiev.socialmediaapi.utils;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Сущность ответа с ошибкой")
public class ErrorResponse {

    /** Код ошибки */
    @Schema(description = "Код ошибки HTTP", example = "404")
    private int errorCode;

    /** Сообщение об ошибке. */
    @Schema(description = "Сообщение об ошибке")
    private String errorMessage;

    /** Список ошибок */
    @Schema(description = "Список сообщений ошибок")
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
