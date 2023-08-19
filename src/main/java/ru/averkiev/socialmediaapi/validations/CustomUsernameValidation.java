package ru.averkiev.socialmediaapi.validations;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * @author mrGreenNV
 */
@ApiModel(description = "Валидация имени пользователя")
public class CustomUsernameValidation implements ConstraintValidator<CustomUsername, String> {

    @ApiModelProperty(notes = "Регулярное выражение для валидации имени пользователя", value = "User_537")
    private final static Pattern LOGIN_PATTERN = Pattern.compile("^[a-zA-Z0-9]*_?[a-zA-Z0-9]*$");

    @ApiModelProperty(notes = "Минимальное количество символов в имени пользователя")
    private int min;

    @ApiModelProperty(notes = "Максимальное количество символов в имени пользователя")
    private int max;

    @ApiModelProperty(notes = "Инициализирует валидацию с установкой граничных значений")
    @Override
    public void initialize(CustomUsername constraintAnnotation) {
        this.min = 6;
        this.max = 254;
    }

    @ApiModelProperty(notes = "Проверяет валидность имени пользователя")
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }

        int length = value.length();

        return length >= min && length <= max && LOGIN_PATTERN.matcher(value).matches();
    }
}
