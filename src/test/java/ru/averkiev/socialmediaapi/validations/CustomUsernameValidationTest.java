package ru.averkiev.socialmediaapi.validations;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Класс содержит тесты для проверки функциональности {@link CustomUsernameValidation}.
 * Тесты охватывают различные сценарии при валидации имени пользователя.
 * Используются моки сервисов для имитации работы с реальными объектами.
 * @author mrGreenNV
 */
class CustomUsernameValidationTest {

    /** Объявление ссылки тестируемого типа. */
    private CustomUsernameValidation validator;

    /** Объявление ссылки на заглушку контекста. */
    private ConstraintValidatorContext context;

    /**
     * Выполняется перед тестами.
     */
    @BeforeEach
    void setUp() {
        validator = new CustomUsernameValidation();
        CustomUsername annotation = mock(CustomUsername.class);
        validator.initialize(annotation);
        context = mock(ConstraintValidatorContext.class);
    }

    /**
     * Проверяет валидацию корректных имен пользователя.
     */
    @Test
    void testValidUsername() {
        assertTrue(validator.isValid("john_doe123", context));
        assertTrue(validator.isValid("user123", context));
        assertTrue(validator.isValid("username", context));
    }

    /**
     * Проверяет валидацию некорректных имен пользователя.
     */
    @Test
    void testInvalidUsername() {
        assertFalse(validator.isValid("", context));
        assertFalse(validator.isValid("user@", context));
        assertFalse(validator.isValid("user name", context));
        assertFalse(validator.isValid("longnamelongnamelongnamelongnamelongnamelongnamelongname" +
                "longnamelongnamelongnamelongnamelongnamelongnamelongnamelongnamelongnamelongname" +
                "longnamelongnamelongnamelongnamelongnamelongnamelongnamelongnamelongnamelongname" +
                "longnamelongnamelongnamelongnamelongname", context));
    }

    /**
     * Проверяет валидацию с переданным значением null.
     */
    @Test
    void testNullUsernameIsValid() {
        assertFalse(validator.isValid(null, context));
    }

}