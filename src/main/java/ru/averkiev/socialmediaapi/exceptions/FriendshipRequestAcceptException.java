package ru.averkiev.socialmediaapi.exceptions;

/**
 * Исключение, выбрасывающееся в случае ошибки при создании запроса дружбы.
 * @author mrGreenNV
 */
public class FriendshipRequestAcceptException extends RuntimeException {

    /**
     * Создаёт новый экземпляр исключения с указанным сообщением об ошибке.
     * @param msg - сообщение об ошибке.
     */
    public FriendshipRequestAcceptException(String msg) {
        super(msg);
    }

    /**
     * Создаёт новый экземпляр исключения с указанным сообщением и причиной.
     * @param msg   - сообщение об ошибке.
     * @param cause - причина исключения.
     */
    public FriendshipRequestAcceptException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
