package ru.averkiev.socialmediaapi.exceptions;

/**
 * Исключение, выбрасывающееся в случае неудачного создания поста.
 * @author mrGreenNV
 */
public class PostCreationException extends RuntimeException {

    /**
     * Создаёт новый экземпляр исключения с указанным сообщением об ошибке.
     * @param msg - сообщение об ошибке.
     */
    public PostCreationException(String msg) {
        super(msg);
    }

    /**
     * Создаёт новый экземпляр исключения с указанным сообщением и причиной.
     * @param msg   - сообщение об ошибке.
     * @param cause - причина исключения.
     */
    public PostCreationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
