package ru.averkiev.socialmediaapi.validations;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @author mrGreenNV
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CustomUsernameValidation.class)
@ApiModel(description = "Аннотация для валидации имени пользователя в системе")
public @interface CustomUsername {

    @ApiModelProperty(notes = "Определяет сообщение, которое будет отображаться при нарушении валидации")
    String message() default "Имя пользователя должно быть от 5 до 255 символов. Допускается использовать латинские буквы цифры и один символ '_'";

    @ApiModelProperty(notes = "Определяет группы ограничений, которым будет принадлежать аннотация")
    Class<?>[] groups() default {};

    @ApiModelProperty(notes = "Определяет нагрузку (payload) для аннотации, которая может быть использована для передачи дополнительной информации в процессе валидации")
    Class<? extends Payload>[] payload() default {};
}
