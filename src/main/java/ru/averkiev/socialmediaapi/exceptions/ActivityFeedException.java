package ru.averkiev.socialmediaapi.exceptions;

/**
 * Исключение, выбрасываемое при возникновении ошибки в ленте активности пользователя.
 * @author mrGreenNV
 */
public class ActivityFeedException extends RuntimeException {

    /**
     * Создаёт новый экземпляр исключения с указанным сообщением об ошибке.
     * @param msg - сообщение об ошибке.
     */
    public ActivityFeedException(String msg) {
        super(msg);
    }

    /**
     * Создаёт новый экземпляр исключения с указанным сообщением и причиной.
     * @param msg   - сообщение об ошибке.
     * @param cause - причина исключения.
     */
    public ActivityFeedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
