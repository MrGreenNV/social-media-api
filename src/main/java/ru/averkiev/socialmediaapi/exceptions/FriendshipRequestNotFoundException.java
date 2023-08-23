package ru.averkiev.socialmediaapi.exceptions;

/**
 * Исключение, выбрасывающееся в случае если запрос дружбы не найден.
 * @author mrGreenNV
 */
public class FriendshipRequestNotFoundException extends RuntimeException {

    /**
     * Создаёт новый экземпляр исключения с указанным сообщением об ошибке.
     * @param msg - сообщение об ошибке.
     */
    public FriendshipRequestNotFoundException(String msg) {
        super(msg);
    }

    /**
     * Создаёт новый экземпляр исключения с указанным сообщением и причиной.
     * @param msg   - сообщение об ошибке.
     * @param cause - причина исключения.
     */
    public FriendshipRequestNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
