package ru.averkiev.socialmediaapi.exceptions;

/**
 * Исключение, выбрасывающееся в случае неудавшейся регистрации пользователя в системе.
 * @author mrGreenNV
 */
public class RegistrationException  extends RuntimeException {

    /**
     * Создаёт новый экземпляр исключения с указанным сообщением об ошибке.
     *
     * @param msg - сообщение об ошибке.
     */
    public RegistrationException(String msg) {
        super(msg);
    }

    /**
     * Создаёт новый экземпляр исключения с указанным сообщением и причиной.
     *
     * @param msg   - сообщение об ошибке.
     * @param cause - причина исключения.
     */
    public RegistrationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
