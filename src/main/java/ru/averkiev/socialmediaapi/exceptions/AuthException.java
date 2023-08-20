package ru.averkiev.socialmediaapi.exceptions;

/**
 * Исключение, выбрасывающееся в случае неудачной аутентификации пользователя в системе.
 * @author mrGreenNV
 */
public class AuthException  extends RuntimeException {

    /**
     * Создаёт новый экземпляр исключения с указанным сообщением об ошибке.
     * @param msg - сообщение об ошибке.
     */
    public AuthException(String msg) {
        super(msg);
    }

    /**
     * Создаёт новый экземпляр исключения с указанным сообщением и причиной.
     * @param msg   - сообщение об ошибке.
     * @param cause - причина исключения.
     */
    public AuthException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

