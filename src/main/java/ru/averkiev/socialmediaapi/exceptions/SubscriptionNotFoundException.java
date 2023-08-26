package ru.averkiev.socialmediaapi.exceptions;

/**
 * Исключение, выбрасываемое в случае если не удалось найти подписки в базе данных.
 * @author mrGreenNV
 */
public class SubscriptionNotFoundException extends RuntimeException {

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке.
     * @param msg - сообщение об ошибке.
     */
    public SubscriptionNotFoundException(String msg) {
        super(msg);
    }

    /**
     * Создаёт экземпляр исключения с указанным сообщением об ошибке и причиной.
     * @param msg - сообщение об ошибке.
     * @param cause - причина исключения.
     */
    public SubscriptionNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}