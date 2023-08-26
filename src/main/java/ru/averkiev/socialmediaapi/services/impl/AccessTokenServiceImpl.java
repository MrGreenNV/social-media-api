package ru.averkiev.socialmediaapi.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.averkiev.socialmediaapi.exceptions.TokenNotFoundException;
import ru.averkiev.socialmediaapi.repositories.AccessTokenRepository;
import ru.averkiev.socialmediaapi.security.AccessToken;
import ru.averkiev.socialmediaapi.services.AccessTokenService;

/**
 * Класс реализует функционал взаимодействия access токена с базой данных (сохранение, обновление, удаление и
 * поиск по идентификатору пользователя, к которому относится токен).
 * @author mrGreenNV
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AccessTokenServiceImpl implements AccessTokenService {

    /** Репозиторий для взаимодействия с базой данных. */
    private final AccessTokenRepository accessTokenRepository;

    /**
     * Сохраняет объект AccessToken в базе данных.
     * @param accessToken сохраняемый токен.
     * @return сохраненный токен.
     */
    @Override
    public AccessToken save(AccessToken accessToken) {
        log.info("IN save - access токен успешно сохранён");
        return accessTokenRepository.save(accessToken);
    }

    /**
     * Обновляет access токен, хранящийся в базе данных.
     * @param userId идентификатор пользователя.
     * @param updateAccessToken обновленный access токен.
     * @return обновленный access токен.
     */
    @Override
    public AccessToken updateByUserId(long userId, AccessToken updateAccessToken) {
        try {
            AccessToken currentAccessToken = findByUserId(userId);
            currentAccessToken.setAccessToken(updateAccessToken.getAccessToken());
            currentAccessToken.setCreatedAt(updateAccessToken.getCreatedAt());
            currentAccessToken.setExpiresAt(updateAccessToken.getExpiresAt());
            log.info("IN updateByUserId - access токен для пользователя с идентификатором: {} успешно обновлен", userId);
            return accessTokenRepository.save(currentAccessToken);
        } catch (TokenNotFoundException tnfEx) {
            log.error("IN updateByUserId - access токен для пользователя с идентификатором: {} не обновлен", userId);
        }
        return null;
    }

    /**
     * Выполняет поиск access токена в базе данных по идентификатору пользователя.
     * @param userId идентификатор пользователя.
     * @return access токен, если поиск дал результат, иначе null.
     * @throws TokenNotFoundException выбрасывает, если токен не был найден.
     */
    @Override
    public AccessToken findByUserId(long userId) throws TokenNotFoundException {
        AccessToken accessToken = accessTokenRepository.findByUserId(userId).orElse(null);

        if (accessToken == null) {
            log.error("IN findByUserId - access токен для пользователя с идентификатором: {} не найден", userId);
            throw new TokenNotFoundException("Токен для пользователя с идентификатором: " + userId + " не найден");
        }
        log.info("IN findByUserId - access токен для пользователя с идентификатором: {} успешно найден", userId);
        return accessToken;
    }

    /**
     * Удаляет access токен из базы данных.
     * @param userId идентификатор пользователя.
     * @throws TokenNotFoundException выбрасывает, если токен не был найден.
     */
    @Override
    public void delete(long userId) throws TokenNotFoundException {
        try {
            AccessToken accessToken = findByUserId(userId);
            accessTokenRepository.delete(accessToken);
            log.info("IN delete - access токен для пользователя с идентификатором: {} успешно удален", userId);
        } catch (TokenNotFoundException tnfEx) {
            log.error("IN delete - access токен для пользователя с идентификатором: {} не удалён", userId);
            throw new TokenNotFoundException("Токен для пользователя с идентификатором: " + userId + " не найден");
        }
    }
}
