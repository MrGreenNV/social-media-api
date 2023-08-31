package ru.averkiev.socialmediaapi.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.averkiev.socialmediaapi.exceptions.TokenNotFoundException;
import ru.averkiev.socialmediaapi.repositories.AccessTokenRepository;
import ru.averkiev.socialmediaapi.security.AccessToken;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Класс содержит тесты для проверки функциональности {@link AccessTokenServiceImpl}.
 * Тесты охватывают различные сценарии при взаимодействии с access токенами.
 * Используются моки сервисов для имитации работы с реальными объектами.
 */
class AccessTokenServiceImplTest {

    /** Заглушка для {@link AccessTokenRepository} */
    @Mock
    private AccessTokenRepository accessTokenRepository;

    /** Внедрение заглушки в {@link AccessTokenServiceImpl} */
    @InjectMocks
    private AccessTokenServiceImpl accessTokenService;

    /**
     * Инициализирует объекты и ресурсы, необходимые для выполнения тестов.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Проверяет, что метод save() класса AccessTokenServiceImpl корректно сохраняет переданный токен в репозитории.
     */
    @Test
    public void testSaveAccessToken() {
        AccessToken accessToken = new AccessToken();
        when(accessTokenRepository.save(any(AccessToken.class))).thenReturn(accessToken);

        AccessToken savedAccessToken = accessTokenService.save(accessToken);

        assertNotNull(savedAccessToken);
        verify(accessTokenRepository, times(1)).save(accessToken);
    }

    /**
     * Проверяет, что метод findByUserId() класса AccessTokenServiceImpl корректно находит токен по идентификатору пользователя.
     */
    @Test
    public void testFindByUserId() {
        long userId = 1L;
        AccessToken accessToken = new AccessToken();
        when(accessTokenRepository.findByUserId(userId)).thenReturn(Optional.of(accessToken));

        AccessToken foundAccessToken = accessTokenService.findByUserId(userId);

        assertNotNull(foundAccessToken);
        verify(accessTokenRepository, times(1)).findByUserId(userId);
    }

    /**
     * Проверяет, что метод findByUserId() правильно обрабатывает случай, когда токен не найден.
     */
    @Test
    public void testFindByUserIdNotFound() {
        long userId = 1L;
        when(accessTokenRepository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(TokenNotFoundException.class, () -> accessTokenService.findByUserId(userId));
    }

    /**
     * Проверяет, что метод updateByUserId() класса AccessTokenServiceImpl корректно обновляет токен по идентификатору пользователя.
     */
    @Test
    public void testUpdateByUserId() {
        long userId = 1L;
        AccessToken currentAccessToken = new AccessToken();
        AccessToken updateAccessToken = new AccessToken();
        when(accessTokenRepository.findByUserId(userId)).thenReturn(Optional.of(currentAccessToken));
        when(accessTokenRepository.save(any(AccessToken.class))).thenReturn(updateAccessToken);

        AccessToken updatedAccessToken = accessTokenService.updateByUserId(userId, updateAccessToken);

        assertNotNull(updatedAccessToken);
        verify(accessTokenRepository, times(1)).findByUserId(userId);
        verify(accessTokenRepository, times(1)).save(currentAccessToken);
    }

    /**
     * Проверяет, что метод updateByUserId() правильно обрабатывает случай, когда токен не найден.
     */
    @Test
    public void testUpdateByUserIdNotFound() {
        long userId = 1L;
        AccessToken updateAccessToken = new AccessToken();
        when(accessTokenRepository.findByUserId(userId)).thenReturn(Optional.empty());

        AccessToken updatedAccessToken = accessTokenService.updateByUserId(userId, updateAccessToken);

        assertNull(updatedAccessToken);
        verify(accessTokenRepository, times(1)).findByUserId(userId);
    }

    /**
     * Проверяет, что метод delete() класса AccessTokenServiceImpl корректно удаляет токен по идентификатору пользователя.
     */
    @Test
    public void testDeleteAccessToken() {
        long userId = 1L;
        AccessToken accessToken = new AccessToken();
        when(accessTokenRepository.findByUserId(userId)).thenReturn(Optional.of(accessToken));

        assertDoesNotThrow(() -> accessTokenService.delete(userId));
        verify(accessTokenRepository, times(1)).findByUserId(userId);
        verify(accessTokenRepository, times(1)).delete(accessToken);
    }

    /**
     * Проверяет, что метод delete() правильно обрабатывает случай, когда токен не найден.
     */
    @Test
    public void testDeleteAccessTokenNotFound() {
        long userId = 1L;
        when(accessTokenRepository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(TokenNotFoundException.class, () -> accessTokenService.delete(userId));
        verify(accessTokenRepository, times(1)).findByUserId(userId);
    }
}