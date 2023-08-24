package ru.averkiev.socialmediaapi.exceptions;

/**
 * Исключение, выбрасываемое когда сообщение не найдено в базе данных.
 * @author mrGreenNV
 */
public class MessageNotFoundException extends RuntimeException {

    /**
     * Создаёт новый экземпляр исключения с указанным сообщением об ошибке.
     * @param msg - сообщение об ошибке.
     */
    public MessageNotFoundException(String msg) {
        super(msg);
    }

    /**
     * Создаёт новый экземпляр исключения с указанным сообщением и причиной.
     * @param msg   - сообщение об ошибке.
     * @param cause - причина исключения.
     */
    public MessageNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
