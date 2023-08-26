package ru.averkiev.socialmediaapi.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.averkiev.socialmediaapi.exceptions.TokenNotFoundException;
import ru.averkiev.socialmediaapi.repositories.RefreshTokenRepository;
import ru.averkiev.socialmediaapi.security.RefreshToken;
import ru.averkiev.socialmediaapi.services.RefreshTokenService;

/**
 * Класс реализует функционал взаимодействия refresh токена с базой данных (сохранение, обновление, удаление и
 * поиск по идентификатору пользователя, к которому относится токен).
 * @author mrGreenNV
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    /** Репозиторий для взаимодействия с базой данных. */
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * Сохраняет объект RefreshToken в базе данных.
     * @param refreshToken сохраняемый токен.
     * @return сохраненный токен.
     */
    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        log.info("IN save - refresh токен успешно сохранён");
        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Обновляет refresh токен, хранящийся в базе данных.
     * @param userId идентификатор пользователя.
     * @param updateRefreshToken обновленный refresh токен.
     * @return обновленный refresh токен.
     */
    @Override
    public RefreshToken updateByUserId(long userId, RefreshToken updateRefreshToken) {
        try {
            RefreshToken currentRefreshToken = findByUserId(userId);
            currentRefreshToken.setRefreshToken(updateRefreshToken.getRefreshToken());
            currentRefreshToken.setCreatedAt(updateRefreshToken.getCreatedAt());
            currentRefreshToken.setExpiresAt(updateRefreshToken.getExpiresAt());
            log.info("IN updateByUserId - refresh токен для пользователя с идентификатором: {} успешно обновлен", userId);
            return refreshTokenRepository.save(currentRefreshToken);
        } catch (TokenNotFoundException tnfEx) {
            log.error("IN updateByUserId - refresh токен для пользователя с идентификатором: {} не обновлен", userId);
        }
        return null;
    }

    /**
     * Выполняет поиск refresh токена в базе данных по идентификатору пользователя.
     * @param userId идентификатор пользователя.
     * @return refresh токен, если поиск дал результат, иначе null.
     * @throws TokenNotFoundException выбрасывает, если токен не был найден.
     */
    @Override
    public RefreshToken findByUserId(long userId) throws TokenNotFoundException {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId).orElse(null);

        if (refreshToken == null) {
            log.error("IN findByUserId - refresh токен для пользователя с идентификатором: {} не найден", userId);
            throw new TokenNotFoundException("Токен для пользователя с идентификатором: " + userId + " не найден");
        }
        log.info("IN findByUserId - refresh токен для пользователя с идентификатором: {} успешно найден", userId);
        return refreshToken;
    }

    /**
     * Удаляет refresh токен из базы данных.
     * @param userId идентификатор пользователя.
     * @throws TokenNotFoundException выбрасывает, если токен не был найден.
     */
    @Override
    public void delete(long userId) throws TokenNotFoundException {
        try {
            RefreshToken refreshToken = findByUserId(userId);
            refreshTokenRepository.delete(refreshToken);
            log.info("IN delete - refresh токен для пользователя с идентификатором: {} успешно удален", userId);
        } catch (TokenNotFoundException tnfEx) {
            log.error("IN delete - refresh токен для пользователя с идентификатором: {} не удалён", userId);
            throw new TokenNotFoundException("Токен для пользователя с идентификатором: " + userId + " не найден");
        }
    }
}
