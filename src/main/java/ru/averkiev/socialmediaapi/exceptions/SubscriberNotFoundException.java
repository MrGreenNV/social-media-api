package ru.averkiev.socialmediaapi.exceptions;

/**
 * Исключение, выбрасываемое в случае если не удалось найти подписчика в базе данных.
 * @author mrGreenNV
 */
public class SubscriberNotFoundException extends RuntimeException {

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке.
     * @param msg - сообщение об ошибке.
     */
    public SubscriberNotFoundException(String msg) {
        super(msg);
    }

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке и причиной.
     * @param msg - сообщение об ошибке.
     * @param cause - причина исключения.
     */
    public SubscriberNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}