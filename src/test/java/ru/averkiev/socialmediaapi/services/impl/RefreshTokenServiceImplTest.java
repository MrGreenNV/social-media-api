package ru.averkiev.socialmediaapi.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.averkiev.socialmediaapi.exceptions.TokenNotFoundException;
import ru.averkiev.socialmediaapi.repositories.RefreshTokenRepository;
import ru.averkiev.socialmediaapi.security.RefreshToken;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Класс содержит тесты для проверки функциональности {@link RefreshTokenServiceImpl}.
 * Тесты охватывают различные сценарии при взаимодействии с refresh токенами.
 * Используются моки сервисов для имитации работы с реальными объектами.
 */
class RefreshTokenServiceImplTest {

    /** Заглушка для {@link RefreshTokenRepository} */
    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    /** Внедрение заглушки в {@link RefreshTokenServiceImpl} */
    @InjectMocks
    private RefreshTokenServiceImpl refreshTokenService;

    /**
     * Инициализирует объекты и ресурсы, необходимые для выполнения тестов.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Проверяет, что метод save() класса RefreshTokenServiceImpl корректно сохраняет переданный токен в репозитории.
     */
    @Test
    public void testSaveAccessToken() {
        RefreshToken refreshToken = new RefreshToken();
        when(refreshTokenRepository.save(any(RefreshToken.class))).thenReturn(refreshToken);

        RefreshToken savedRefreshToken = refreshTokenService.save(refreshToken);

        assertNotNull(savedRefreshToken);
        verify(refreshTokenRepository, times(1)).save(refreshToken);
    }

    /**
     * Проверяет, что метод findByUserId() класса RefreshTokenServiceImpl корректно находит токен по идентификатору пользователя.
     */
    @Test
    public void testFindByUserId() {
        long userId = 1L;
        RefreshToken refreshToken = new RefreshToken();
        when(refreshTokenRepository.findByUserId(userId)).thenReturn(Optional.of(refreshToken));

        RefreshToken foundRefreshToken = refreshTokenService.findByUserId(userId);

        assertNotNull(foundRefreshToken);
        verify(refreshTokenRepository, times(1)).findByUserId(userId);
    }

    /**
     * Проверяет, что метод findByUserId() правильно обрабатывает случай, когда токен не найден.
     */
    @Test
    public void testFindByUserIdNotFound() {
        long userId = 1L;
        when(refreshTokenRepository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(TokenNotFoundException.class, () -> refreshTokenService.findByUserId(userId));
    }

    /**
     * Проверяет, что метод updateByUserId() класса RefreshTokenServiceImpl корректно обновляет токен по идентификатору пользователя.
     */
    @Test
    public void testUpdateByUserId() {
        long userId = 1L;
        RefreshToken currentRefreshToken = new RefreshToken();
        RefreshToken updateRefreshToken = new RefreshToken();
        when(refreshTokenRepository.findByUserId(userId)).thenReturn(Optional.of(currentRefreshToken));
        when(refreshTokenRepository.save(any(RefreshToken.class))).thenReturn(updateRefreshToken);

        RefreshToken updatedRefreshToken = refreshTokenService.updateByUserId(userId, updateRefreshToken);

        assertNotNull(updatedRefreshToken);
        verify(refreshTokenRepository, times(1)).findByUserId(userId);
        verify(refreshTokenRepository, times(1)).save(currentRefreshToken);
    }

    /**
     * Проверяет, что метод updateByUserId() правильно обрабатывает случай, когда токен не найден.
     */
    @Test
    public void testUpdateByUserIdNotFound() {
        long userId = 1L;
        RefreshToken updateRefreshToken = new RefreshToken();
        when(refreshTokenRepository.findByUserId(userId)).thenReturn(Optional.empty());

        RefreshToken updatedRefreshToken = refreshTokenService.updateByUserId(userId, updateRefreshToken);

        assertNull(updatedRefreshToken);
        verify(refreshTokenRepository, times(1)).findByUserId(userId);
    }

    /**
     * Проверяет, что метод delete() класса RefreshTokenServiceImpl корректно удаляет токен по идентификатору пользователя.
     */
    @Test
    public void testDeleteAccessToken() {
        long userId = 1L;
        RefreshToken refreshToken = new RefreshToken();
        when(refreshTokenRepository.findByUserId(userId)).thenReturn(Optional.of(refreshToken));

        assertDoesNotThrow(() -> refreshTokenService.delete(userId));
        verify(refreshTokenRepository, times(1)).findByUserId(userId);
        verify(refreshTokenRepository, times(1)).delete(refreshToken);
    }

    /**
     * Проверяет, что метод delete() правильно обрабатывает случай, когда токен не найден.
     */
    @Test
    public void testDeleteAccessTokenNotFound() {
        long userId = 1L;
        when(refreshTokenRepository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThrows(TokenNotFoundException.class, () -> refreshTokenService.delete(userId));
        verify(refreshTokenRepository, times(1)).findByUserId(userId);
    }
}