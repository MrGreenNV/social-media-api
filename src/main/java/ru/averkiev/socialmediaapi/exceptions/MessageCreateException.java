package ru.averkiev.socialmediaapi.exceptions;

/**
 * Исключение, выбрасываемое когда пользователи не являются друзьями.
 * @author mrGreenNV
 */
public class MessageCreateException extends RuntimeException {

    /**
     * Создаёт новый экземпляр исключения с указанным сообщением об ошибке.
     * @param msg - сообщение об ошибке.
     */
    public MessageCreateException(String msg) {
        super(msg);
    }

    /**
     * Создаёт новый экземпляр исключения с указанным сообщением и причиной.
     * @param msg   - сообщение об ошибке.
     * @param cause - причина исключения.
     */
    public MessageCreateException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
