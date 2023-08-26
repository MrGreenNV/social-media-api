package ru.averkiev.socialmediaapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.averkiev.socialmediaapi.models.UserCreateDTO;
import ru.averkiev.socialmediaapi.models.UserFriendDTO;
import ru.averkiev.socialmediaapi.services.impl.UserServiceImpl;

import java.util.List;

/**
 * Класс представляет собой REST-контроллер для взаимодействия с пользователями в системе.
 * Этот класс предоставляет API-endpoints для выполнения операций регистрации пользователей, обновления их данных.
 * Все запросы выполняются в формате JSON.
 * @author mrGreenNV
 */
@RestController
@RequestMapping("/social-media-api/users")
@RequiredArgsConstructor
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
     * @return данные зарегистрированного пользователя.
     */
    @PostMapping("register")
    @Operation(
            summary = "Регистрация пользователя в системе",
            description = "Позволяет зарегистрировать пользователя в системе по указанным имени, паролю и электронной почте."
    )
    public ResponseEntity<UserCreateDTO> register(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        return ResponseEntity.ok(userService.register(userCreateDTO));
    }

    /**
     * API-endpoint для получения информации о всех пользователях, зарегистрированных в системе.
     * @return список пользователей зарегистрированных в системе.
     */
    @GetMapping
    @Operation(
            summary = "Выводит список пользователей",
            description = "Позволяет просмотреть имена и электронные почты всех пользователей, зарегистрированных в системе"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<List<UserFriendDTO>> showAllUser() {
        List<UserFriendDTO> userFriendDTOList = userService.getAllUsers().stream()
                .map(user -> modelMapper.map(user, UserFriendDTO.class))
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(userFriendDTOList);
    }
}
