package ru.averkiev.socialmediaapi.validations;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Класс содержит тесты для проверки функциональности {@link CustomEmailValidation}.
 * Тесты охватывают различные сценарии при валидации электронной почты пользователя.
 * Используются моки сервисов для имитации работы с реальными объектами.
 * @author mrGreenNV
 */
class CustomEmailValidationTest {

    /** Объявление ссылки тестируемого типа. */
    private CustomEmailValidation validator;

    /** Объявление ссылки на заглушку контекста. */
    private ConstraintValidatorContext context;

    /**
     * Выполняется перед тестами.
     */
    @BeforeEach
    public void setUp() {
        validator = new CustomEmailValidation();
        context = mock(ConstraintValidatorContext.class);
    }

    /**
     * Проверяет валидацию корректной электронной почты.
     */
    @Test
    public void testValidEmail() {
        assertTrue(validator.isValid("bob_Smith123@mail.ru", context));
        assertTrue(validator.isValid("user123@yandex.ru", context));
        assertTrue(validator.isValid("one+@gmail.com", context));
    }

    /**
     * Проверяет валидацию некорректной электронной почты.
     */
    @Test
    public void testInvalidEmail() {
        assertFalse(validator.isValid("", context));
        assertFalse(validator.isValid("user@", context));
        assertFalse(validator.isValid("@mail.ru", context));
    }

    /**
     * Проверяет валидацию с переданным значением null.
     */
    @Test
    public void testNullEmailIsInvalid() {
        assertFalse(validator.isValid(null, context));
    }
}