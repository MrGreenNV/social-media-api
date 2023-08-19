package ru.averkiev.socialmediaapi.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Аннотация для валидации имени пользователя в системе.
 * @author mrGreenNV
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CustomUsernameValidation.class)
public @interface CustomUsername {

    /**
     * Определяет сообщение, которое будет отображаться при нарушении валидации.
     * @return сообщение.
     */
    String message() default "Имя пользователя должно быть от 5 до 255 символов. Допускается использовать латинские буквы цифры и один символ '_'";

    /**
     * Определяет группы ограничений, которым будет принадлежать аннотация.
     * @return группы ограничений.
     */
    Class<?>[] groups() default {};

    /**
     * Определяет нагрузку (payload) для аннотации, которая может быть использована для передачи дополнительной
     * информации в процессе валидации.
     * @return нагрузка для аннотации.
     */
    Class<? extends Payload>[] payload() default {};
}
