package ru.averkiev.socialmediaapi.services;

import ru.averkiev.socialmediaapi.exceptions.TokenNotFoundException;
import ru.averkiev.socialmediaapi.security.RefreshToken;

/**
 * Интерфейс представляет собой функционал для сохранения, обновления, поиска и удаления refresh токена.
 * @author mrGreenNV
 */
public interface RefreshTokenService {

    /**
     * Сохраняет объект RefreshToken в базе данных.
     * @param refreshToken сохраняемый токен.
     * @return сохраненный токен.
     */
    RefreshToken save(RefreshToken refreshToken);

    /**
     * Обновляет refresh токен, хранящийся в базе данных.
     * @param userId идентификатор пользователя.
     * @param updateRefreshToken обновленный refresh токен.
     * @return обновленный refresh токен.
     */
    RefreshToken updateByUserId(long userId, RefreshToken updateRefreshToken);

    /**
     * Выполняет поиск refresh токена в базе данных по идентификатору пользователя.
     * @param userId идентификатор пользователя.
     * @return refresh токен, если поиск дал результат, иначе null.
     * @throws TokenNotFoundException выбрасывает, если токен не был найден.
     */
    RefreshToken findByUserId(long userId) throws TokenNotFoundException;

    /**
     * Удаляет refresh токен из базы данных.
     * @param userId идентификатор пользователя.
     * @throws TokenNotFoundException выбрасывает, если токен не был найден.
     */
    void delete(long userId) throws TokenNotFoundException;
}
