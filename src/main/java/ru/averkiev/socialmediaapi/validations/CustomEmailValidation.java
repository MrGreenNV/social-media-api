package ru.averkiev.socialmediaapi.validations;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * @author mrGreenNV
 */
@ApiModel(description = "Валидация электронной почты")
public class CustomEmailValidation implements ConstraintValidator<CustomEmail, String> {

    @ApiModelProperty(notes = "Регулярное выражение для проверки электронной почты")
    private final static Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+.+.[A-Za-z]{2,4}$");

    @Override
    @ApiModelProperty(notes = "Инициализирует валидацию с установкой граничных значений")
    public void initialize(CustomEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    @ApiModelProperty(notes = "Проверяет валидность электронной почты")
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        if (value == null) {
            return true;
        }

        return EMAIL_PATTERN.matcher(value).matches();
    }
}
