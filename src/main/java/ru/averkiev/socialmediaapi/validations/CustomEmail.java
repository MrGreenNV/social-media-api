package ru.averkiev.socialmediaapi.validations;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * @author mrGreenNV
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CustomEmailValidation.class)
@ApiModel(description = "Аннотация для валидации электронной почты")
public @interface CustomEmail {

    @ApiModelProperty(notes = "Определяет сообщение, которое будет отображаться при нарушении валидации")
    String message() default "Email должен быть валидным";

    @ApiModelProperty(notes = "Определяет группы ограничений, которым будет принадлежать аннотация")
    Class<?>[] groups() default {};

    @ApiModelProperty(notes = "Определяет нагрузку (payload) для аннотации, которая может быть использована для передачи дополнительной информации в процессе валидации")
    Class<? extends Payload>[] payload() default {};
}
