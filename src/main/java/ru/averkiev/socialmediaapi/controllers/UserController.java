package ru.averkiev.socialmediaapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.averkiev.socialmediaapi.exceptions.RegistrationException;
import ru.averkiev.socialmediaapi.models.UserCreateDTO;
import ru.averkiev.socialmediaapi.models.UserFriendDTO;
import ru.averkiev.socialmediaapi.services.impl.UserServiceImpl;
import ru.averkiev.socialmediaapi.utils.ErrorResponse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс представляет собой REST-контроллер для взаимодействия с пользователями в системе.
 * Этот класс предоставляет API-endpoints для выполнения операций регистрации пользователей, обновления их данных.
 * Все запросы выполняются в формате JSON.
 * @author mrGreenNV
 */
@RestController
@RequestMapping("/social-media-api/users")
@RequiredArgsConstructor
@EnableMethodSecurity
@Tag(
        name = "UserController",
        description = "Позволяет регистрировать новых пользователей"
)
public class UserController {

    /** Сервис для взаимодействия с пользователями. */
    private final UserServiceImpl userService;

    /** ModelMapper для преобразования моделей с DTO */
    private final ModelMapper modelMapper;

    /**
     * API-endpoint для регистрации нового пользователя.
     * @param userCreateDTO данные для регистрации нового пользователя.
     * @return данные зарегистрированного пользователя или сообщение об ошибке.
     */
    @PostMapping("register")
    @Operation(
            summary = "Регистрация пользователя в системе",
            description = "Позволяет зарегистрировать пользователя в системе по указанным имени, паролю и электронной почте."
    )
    public ResponseEntity<?> register(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        try {
            return ResponseEntity.ok(userService.register(userCreateDTO));
        } catch (RegistrationException regEx) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), regEx.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * API-endpoint для получения информации о всех пользователях, зарегистрированных в системе.
     * @return список пользователей зарегистрированных в системе.
     */
    @Operation(
            summary = "Выводит список пользователей",
            description = "Позволяет просмотреть имена и электронные почты всех пользователей, зарегистрированных в системе"
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping
    public ResponseEntity<?> showAllUser() {
        List<UserFriendDTO> userFriendDTOList = userService.getAllUsers().stream()
                .map(user -> modelMapper.map(user, UserFriendDTO.class))
                .toList();
        return ResponseEntity.ok(userFriendDTOList);
    }

    /**
     * Позволяет обработать ошибки связанные с валидацией пользовательских данных.
     * @param ex ошибки при валидации данных.
     * @return объект ErrorResponse, содержащий информацию об ошибках.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        List<String> errorMessages = fieldErrors.stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation failed", errorMessages);
    }
}
