package ru.averkiev.socialmediaapi.exceptions;

/**
 * Исключение, выбрасываемое в случае если не удалось найти пост пользователя в базе данных.
 * @author mrGreenNV
 */
public class PostNotFoundException extends RuntimeException {

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке.
     * @param msg - сообщение об ошибке.
     */
    public PostNotFoundException(String msg) {
        super(msg);
    }

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке и причиной.
     * @param msg - сообщение об ошибке.
     * @param cause - причина исключения.
     */
    public PostNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
