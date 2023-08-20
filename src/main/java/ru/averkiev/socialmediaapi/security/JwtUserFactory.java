package ru.averkiev.socialmediaapi.security;

import ru.averkiev.socialmediaapi.models.Status;
import ru.averkiev.socialmediaapi.models.User;

/**
 * Класс предоставляет статические методы для создания объекта JwtUser из объекта User. Этот класс упрощает процесс
 * создания JwtUser для использования в генерации и проверке JWT токенов в системе.
 * @author mrGreenNV
 */
public class JwtUserFactory {

    /**
     * Конструктор запрещает создание объекта.
     */
    private JwtUserFactory() {

    }

    /**
     * Создаёт и возвращает объект JwtUser на основе переданного объекта User. Преобразует поля объекта User
     * в соответствующие поля JwtUser.
     * @param user - переданный пользователь, которого необходимо преобразовать в объект JwtUser
     * @return - объект JwtUser, в которого преобразован User
     */
    public static JwtUser created(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getStatus().equals(Status.ACTIVE)
        );
    }
}
