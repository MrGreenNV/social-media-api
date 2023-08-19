package ru.averkiev.socialmediaapi.services;

import ru.averkiev.socialmediaapi.exceptions.RegistrationException;
import ru.averkiev.socialmediaapi.exceptions.UserNotFoundException;
import ru.averkiev.socialmediaapi.models.User;
import ru.averkiev.socialmediaapi.models.UserCreateDTO;

import java.util.List;

/**
 * Интерфейс определяет функциональность для управления пользователями.
 * @author mrGreenNV
 */
public interface UserService {

    /**
     * Регистрирует нового пользователя в системе.
     * @param userCreateDTO DTO данные нового пользователя.
     * @return данные зарегистрированного пользователя.
     * @throws RegistrationException выбрасывает если регистрация пользователя не удалась по каким-либо причинам.
     */
    UserCreateDTO register(UserCreateDTO userCreateDTO) throws RegistrationException;

    /**
     * Сохраняет нового пользователя в системе.
     * @param user новый пользователь.
     * @return созданный пользователь.
     */
    User saveUser(User user);

    /**
     * Возвращает пользователя по его идентификатору.
     * @param userId идентификатор искомого пользователя.
     * @return Optional, содержащий найденного пользователя, или пустой Optional, если пользователь не найден.
     * @throws UserNotFoundException выбрасывает, если пользователь с заданным идентификатором не найден.
     */
    User getUserById(Long userId) throws UserNotFoundException;

    /**
     * Возвращает пользователя по его логину.
     * @param username имя пользователя в системе.
     * @return Optional, содержащий найденного пользователя, или пустой Optional, если пользователь не найден.
     * @throws UserNotFoundException выбрасывает, если пользователь с заданным именем не найден.
     */
    User getUserByUsername(String username) throws UserNotFoundException;

    /**
     * Возвращает список всех пользователей.
     * @return список пользователей.
     */
    List<User> getAllUsers();
}
