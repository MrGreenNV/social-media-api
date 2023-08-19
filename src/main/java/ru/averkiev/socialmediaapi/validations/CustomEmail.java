package ru.averkiev.socialmediaapi.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Аннотация для валидации электронной почты.
 * @author mrGreenNV
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CustomEmailValidation.class)
public @interface CustomEmail {

    /**
     * Определяет сообщение, которое будет отображаться при нарушении валидации.
     * @return сообщение.
     */
    String message() default "Email должен быть валидным";

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
