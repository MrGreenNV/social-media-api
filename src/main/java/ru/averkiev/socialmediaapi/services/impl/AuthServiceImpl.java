package ru.averkiev.socialmediaapi.services.impl;

import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.averkiev.socialmediaapi.exceptions.AuthException;
import ru.averkiev.socialmediaapi.exceptions.TokenNotFoundException;
import ru.averkiev.socialmediaapi.exceptions.UserNotFoundException;
import ru.averkiev.socialmediaapi.security.*;
import ru.averkiev.socialmediaapi.services.AuthService;

/**
 * Класс предоставляет функционал для аутентификации и авторизации пользователей.
 * @author mrGreenNV
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    /** Сервис для получения пользователя. */
    private final JwtUserDetailsServiceImpl jwtUserDetailsService;

    /** Сервис для взаимодействия с access токеном и базой данных. */
    private final AccessTokenServiceImpl accessTokenService;

    /** Сервис для взаимодействия с refresh токеном и базой данных. */
    private final RefreshTokenServiceImpl refreshTokenService;

    /** Сервис для взаимодействия с токенами, их созданием, валидацией и обновлением. */
    private final JwtProvider jwtProvider;

    /** Сервис для взаимодействия с хэшированными паролями. */
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Осуществляет вход пользователя в систему
     * @param authRequest запрос аутентификации.
     * @return объект JwtResponse содержащий пару токенов.
     */
    @Override
    public JwtResponse login(JwtRequest authRequest) {

        final JwtUser jwtUser;

        // Получения пользователя из базы данных по логину.
        try {
            jwtUser = (JwtUser) jwtUserDetailsService.loadUserByUsername(authRequest.getUsername());
        } catch (UserNotFoundException usfEx) {
            throw new AuthException("Пользователь с именем: " + authRequest.getUsername() + " не зарегистрирован в системе");
        }

        // Сравнение пароля, полученного из запроса аутентификации с паролем, полученным из базы данных.
        if (passwordEncoder.matches(authRequest.getPassword(), jwtUser.getPassword())) {
            // Генерация access токена.
            final String accessTokenStr = jwtProvider.generateAccessToken(jwtUser);

            // Создание экземпляра AccessToken.
            final AccessToken accessToken = new AccessToken(
                    jwtUser.getId(),
                    accessTokenStr,
                    jwtProvider.getAccessClaims(accessTokenStr).getIssuedAt(),
                    jwtProvider.getAccessClaims(accessTokenStr).getExpiration()
            );

            // Сохранение access токена в базе данных.
            try {
                accessTokenService.findByUserId(jwtUser.getId());
                accessTokenService.updateByUserId(jwtUser.getId(), accessToken);
                log.info("IN login - access токен найден и успешно обновлен");
            } catch (TokenNotFoundException tnfEx) {
                accessTokenService.save(accessToken);
                log.info("IN login - access токен не найден и успешно сохранен как новый");
            }

            // Генерация refresh токена.
            final String refreshTokenStr = jwtProvider.generateRefreshToken(jwtUser);

            // Создание экземпляра RefreshToken.
            final RefreshToken refreshToken = new RefreshToken(
                    jwtUser.getId(),
                    refreshTokenStr,
                    jwtProvider.getRefreshClaims(refreshTokenStr).getIssuedAt(),
                    jwtProvider.getRefreshClaims(refreshTokenStr).getExpiration()
            );

            // Сохранение refresh токена в базе данных.
            try {
                refreshTokenService.findByUserId(jwtUser.getId());
                refreshTokenService.updateByUserId(jwtUser.getId(), refreshToken);
                log.info("IN login - refresh токен найден и успешно обновлен");
            } catch (TokenNotFoundException tnfEx) {
                refreshTokenService.save(refreshToken);
                log.info("IN login - refresh токен не найден и успешно сохранен как новый");
            }

            log.info("IN login - вход в систему пользователем: {}  успешно осуществлен", jwtUser.getUsername());
            return new JwtResponse(accessToken.getAccessToken(), refreshToken.getRefreshToken());
        } else {
            log.error("IN login - вход в систему пользователем: {}  не осуществлен", jwtUser.getUsername());
            throw new AuthException("Неправильный пароль");
        }
    }

    /**
     * Позволяет получить новый AccessToken по заданному refresh токену.
     * @param refreshToken refresh токен.
     * @return объект JwtResponse содержащий новый access токен.
     */
    @Override
    public JwtResponse getAccessToken(RefreshToken refreshToken) throws AuthException {

        if (jwtProvider.validateRefreshToken(refreshToken.getRefreshToken())) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken.getRefreshToken());
            final String username = claims.getSubject();

            final JwtUser jwtUser = (JwtUser) jwtUserDetailsService.loadUserByUsername(username);
            try {
                final RefreshToken saveRefreshToken = refreshTokenService.findByUserId(jwtUser.getId());
                if (saveRefreshToken.getRefreshToken().equals(refreshToken.getRefreshToken())) {
                    // Генерация access токена.
                    final String accessTokenStr = jwtProvider.generateAccessToken(jwtUser);

                    // Создание экземпляра AccessToken.
                    final AccessToken newAccessToken = new AccessToken(
                            jwtUser.getId(),
                            accessTokenStr,
                            jwtProvider.getAccessClaims(accessTokenStr).getIssuedAt(),
                            jwtProvider.getAccessClaims(accessTokenStr).getExpiration()
                    );

                    // Обновление access токена в базе данных.
                    accessTokenService.updateByUserId(jwtUser.getId(), newAccessToken);
                    log.info("IN getAccessToken - access токен для пользователя: {} успешно обновлен", username);
                    return new JwtResponse(accessTokenStr, null);
                }
            } catch (TokenNotFoundException tnfEx) {
                log.error("IN getAccessToken - access токен для пользователя: {} не обновлен", username, tnfEx);
            }
        }
        throw new AuthException("Неизвестный JWT токен");
    }

    /**
     * Позволяет обновить пару токенов на заданному refresh токену.
     * @param refreshToken refresh токен.
     * @return объект JwtResponse содержащий пару новых токенов.
     */
    @Override
    public JwtResponse refresh(RefreshToken refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken.getRefreshToken())) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken.getRefreshToken());
            final String username = claims.getSubject();

            final JwtUser jwtUser = (JwtUser) jwtUserDetailsService.loadUserByUsername(username);
            try {
                final RefreshToken saveRefreshToken = refreshTokenService.findByUserId(jwtUser.getId());
                if (saveRefreshToken.getRefreshToken().equals(refreshToken.getRefreshToken())) {
                    // Генерация access токена.
                    final String accessTokenStr = jwtProvider.generateAccessToken(jwtUser);

                    // Создание экземпляра AccessToken.
                    final AccessToken newAccessToken = new AccessToken(
                            jwtUser.getId(),
                            accessTokenStr,
                            jwtProvider.getAccessClaims(accessTokenStr).getIssuedAt(),
                            jwtProvider.getAccessClaims(accessTokenStr).getExpiration()
                    );

                    // Генерация refresh токена.
                    final String refreshTokenStr = jwtProvider.generateRefreshToken(jwtUser);

                    // Создание экземпляра RefreshToken.
                    final RefreshToken newRefreshToken = new RefreshToken(
                            jwtUser.getId(),
                            refreshTokenStr,
                            jwtProvider.getRefreshClaims(refreshTokenStr).getIssuedAt(),
                            jwtProvider.getRefreshClaims(refreshTokenStr).getExpiration()
                    );

                    // Обновление access токена в базе данных.
                    accessTokenService.updateByUserId(jwtUser.getId(), newAccessToken);
                    log.info("IN getAccessToken - access токен для пользователя: {} успешно обновлен", username);

                    // Обновление refresh токена в базе данных.
                    refreshTokenService.updateByUserId(jwtUser.getId(), newRefreshToken);
                    log.info("IN getAccessToken - refresh токен для пользователя: {} успешно обновлен", username);

                    return new JwtResponse(accessTokenStr, null);
                }
            } catch (TokenNotFoundException tnfEx) {
                log.error("IN getAccessToken - токены для пользователя: {} не обновлен", username, tnfEx);
            }
        }
        throw new AuthException("Неизвестный JWT токен");
    }

    /**
     * Позволяет получить информацию об аутентификации.
     * @return объект JwtAuthentication.
     */
    @Override
    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Позволяет осуществить выход пользователя из системы, при этом access и refresh токены удаляются.
     * @param refreshToken refresh токен.
     * @return true, если выход осуществлен иначе - false.
     */
    @Override
    @Transactional
    public boolean logout(RefreshToken refreshToken) {

        if (jwtProvider.validateRefreshToken(refreshToken.getRefreshToken())) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken.getRefreshToken());
            final String username = claims.getSubject();

            final JwtUser jwtUser = (JwtUser) jwtUserDetailsService.loadUserByUsername(username);
            refreshTokenService.delete(jwtUser.getId());
            accessTokenService.delete(jwtUser.getId());

            return true;
        }
        return false;
    }

    /**
     * Осуществляет валидацию refresh токена и, если требуется, обновляет его.
     * @param refreshToken refresh токен.
     * @return true, если токен валиден, иначе - false.
     */
    @Override
    public boolean validateRefreshToken(RefreshToken refreshToken) throws AuthException {
        if (jwtProvider.isRefreshTokenExpired(refreshToken.getRefreshToken())) {
            try {
                JwtResponse jwtResponse = refresh(refreshToken);
                refreshToken.setRefreshToken(jwtResponse.getRefreshToken());
                log.info("IN validateRefreshToken - токены успешно обновлены");

            } catch (Exception ex) {
                throw new AuthException("Обновление токенов невозможно, требуется войти в систему повторно");
            }
        }
        return jwtProvider.validateRefreshToken(refreshToken.getRefreshToken());
    }

    /**
     * Осуществляет валидацию access токена и, если требуется, обновляет его.
     * @param accessToken access токен.
     * @return true, если токен валиден, иначе - false.
     */
    @Override
    public boolean validateAccessToken(AccessToken accessToken) {
        if (jwtProvider.isAccessTokenExpired(accessToken.getAccessToken())) {
            final Claims claims = jwtProvider.getRefreshClaims(accessToken.getAccessToken());
            final String username = claims.getSubject();

            final JwtUser jwtUser = (JwtUser) jwtUserDetailsService.loadUserByUsername(username);
            RefreshToken refreshToken = refreshTokenService.findByUserId(jwtUser.getId());

            JwtResponse jwtResponse = getAccessToken(refreshToken);
            accessToken.setAccessToken(jwtResponse.getAccessToken());
            log.info("IN validateAccessToken - access токен успешно обновлен");
        }
        return jwtProvider.validateAccessToken(accessToken.getAccessToken());
    }
}
