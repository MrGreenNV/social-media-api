package ru.averkiev.socialmediaapi.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * Класс представляет собой валидатор для проверки имени пользователя в системе.
 * @author mrGreenNV
 */
public class CustomUsernameValidation implements ConstraintValidator<CustomUsername, String> {

    /** Регулярное выражение для валидного имени пользователя в системе. */
    private final static Pattern LOGIN_PATTERN = Pattern.compile("^[a-zA-Z0-9]*_?[a-zA-Z0-9]*$");

    /** Минимальное количество символов в имени пользователя в системе. */
    private int min;

    /** Максимальное количество символов в имени пользователя в системе. */
    private int max;

    /**
     * Инициализирует валидацию, устанавливая граничные значения.
     * @param constraintAnnotation аннотация
     */
    @Override
    public void initialize(CustomUsername constraintAnnotation) {
        this.min = 6;
        this.max = 254;
    }

    /**
     * Проверяет валидность имени пользователя в системе.
     * @param value имя пользователя в системе.
     * @param context контекст.
     * @return true если имя пользователя в системе валидно, иначе false
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        int length = value.length();

        return length >= min && length <= max && LOGIN_PATTERN.matcher(value).matches();
    }
}
