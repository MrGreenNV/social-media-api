package ru.averkiev.socialmediaapi.exceptions;

/**
 * Исключение, выбрасываемое в случае если не удалось найти пользователя в базе данных.
 * @author mrGreenNV
 */
public class UserFriendNotFoundException extends RuntimeException {

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке.
     * @param msg - сообщение об ошибке.
     */
    public UserFriendNotFoundException(String msg) {
        super(msg);
    }

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке и причиной.
     * @param msg - сообщение об ошибке.
     * @param cause - причина исключения.
     */
    public UserFriendNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
