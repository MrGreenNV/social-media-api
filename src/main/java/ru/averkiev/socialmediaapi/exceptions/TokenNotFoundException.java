package ru.averkiev.socialmediaapi.exceptions;

/**
 * Исключение, выбрасываемое в случае если не удалось найти токен в базе данных.
 * @author mrGreenNV
 */
public class TokenNotFoundException extends RuntimeException {

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке.
     * @param msg - сообщение об ошибке.
     */
    public TokenNotFoundException(String msg) {
        super(msg);
    }

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке и причиной.
     * @param msg - сообщение об ошибке.
     * @param cause - причина исключения.
     */
    public TokenNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
