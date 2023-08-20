package ru.averkiev.socialmediaapi.services;

import ru.averkiev.socialmediaapi.exceptions.TokenNotFoundException;
import ru.averkiev.socialmediaapi.security.AccessToken;

import java.util.Optional;

/**
 * Интерфейс представляет собой функционал для сохранения, обновления, поиска и удаления access токена.
 * @author mrGreenNV
 */
public interface AccessTokenService {

    /**
     * Сохраняет объект AccessToken в базе данных.
     * @param accessToken сохраняемый токен.
     * @return сохраненный токен.
     */
    AccessToken save(AccessToken accessToken);

    /**
     * Обновляет access токен, хранящийся в базе данных.
     * @param userId идентификатор пользователя.
     * @param updateAccessToken обновленный access токен.
     * @return обновленный access токен.
     */
    AccessToken updateByUserId(long userId, AccessToken updateAccessToken);

    /**
     * Выполняет поиск access токена в базе данных по идентификатору пользователя.
     * @param userId идентификатор пользователя.
     * @return access токен, если поиск дал результат, иначе null.
     * @throws TokenNotFoundException выбрасывает, если токен не был найден.
     */
    AccessToken findByUserId(long userId) throws TokenNotFoundException;

    /**
     * Удаляет access токен из базы данных.
     * @param userId идентификатор пользователя.
     * @throws TokenNotFoundException выбрасывает, если токен не был найден.
     */
    void delete(long userId) throws TokenNotFoundException ;
}
