package ru.averkiev.socialmediaapi.services;

import ru.averkiev.socialmediaapi.exceptions.AuthException;
import ru.averkiev.socialmediaapi.security.*;

/**
 * Интерфейс определяет функциональность для осуществления входа в систему, получения токенов, их валидации и обновления.
 * @author mrGreenNV
 */
public interface AuthService {

    /**
     * Осуществляет вход пользователя в систему
     * @param authRequest запрос аутентификации.
     * @return объект JwtResponse содержащий пару токенов.
     */
    JwtResponse login(JwtRequest authRequest);

    /**
     * Позволяет получить новый AccessToken по заданному refresh токену.
     * @param refreshToken refresh токен.
     * @return объект JwtResponse содержащий новый access токен.
     * @throws AuthException выбрасывает при ошибке обновления токена.
     */
    JwtResponse getAccessToken(RefreshToken refreshToken) throws AuthException;

    /**
     * Позволяет обновить пару токенов на заданному refresh токену.
     * @param refreshToken refresh токен.
     * @return объект JwtResponse содержащий пару новых токенов.
     * @throws AuthException выбрасывает при ошибке обновления токенов.
     */
    JwtResponse refresh(RefreshToken refreshToken) throws AuthException;

    /**
     * Позволяет получить информацию об аутентификации.
     * @return объект JwtAuthentication.
     */
    JwtAuthentication getAuthInfo();

    /**
     * Позволяет осуществить выход пользователя из системы, при этом access и refresh токены удаляются.
     * @param refreshToken refresh токен.
     * @return true, если выход осуществлен иначе - false.
     */
    boolean logout(RefreshToken refreshToken);

    /**
     * Осуществляет валидацию refresh токена.
     * @param refreshToken refresh токен.
     * @return true, если токен валиден, иначе - false.
     */
    boolean validateRefreshToken(RefreshToken refreshToken) throws AuthException;

    /**
     * Осуществляет валидацию access токена.
     * @param accessToken access токен.
     * @return true, если токен валиден, иначе - false.
     */
    boolean validateAccessToken(AccessToken accessToken);

    /**
     * Позволяет получить идентификатор пользователя из аутентификации.
     * @return идентификатор пользователя.
     * @throws AuthException выбрасывает если возникает ошибка аутентификации.
     */
    Long getUserIdFromAuthentication() throws AuthException;

}
