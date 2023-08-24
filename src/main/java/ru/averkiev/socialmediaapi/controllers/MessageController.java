package ru.averkiev.socialmediaapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.averkiev.socialmediaapi.exceptions.*;
import ru.averkiev.socialmediaapi.models.MessageDTO;
import ru.averkiev.socialmediaapi.models.MessageEditDTO;
import ru.averkiev.socialmediaapi.models.User;
import ru.averkiev.socialmediaapi.services.impl.MessageServiceImpl;
import ru.averkiev.socialmediaapi.services.impl.UserFriendServiceImpl;
import ru.averkiev.socialmediaapi.services.impl.UserServiceImpl;
import ru.averkiev.socialmediaapi.utils.ErrorResponse;

/**
 * Класс представляет собой REST-контроллер для управления сообщениями пользователей.
 * Этот класс предоставляет API-endpoints для создания, редактирования и удаления сообщений.
 * Все запросы выполняются в формате JSON.
 * @author mrGreenNV
 */
@RestController
@RequestMapping("/social-media-api/messages")
@RequiredArgsConstructor
@Tag(
        name = "MessageController",
        description = "Позволяет управлять сообщениями между пользователями"
)
public class MessageController {

    /** Сервис для взаимодействия с сообщениями. */
    private final MessageServiceImpl messageService;

    /** Сервис для взаимодействия с дружескими связями. */
    private final UserFriendServiceImpl userFriendService;

    /** Сервис для взаимодействия с пользователями. */
    private final UserServiceImpl userService;

    /**
     * API-endpoint для создания сообщения.
     * @param messageDTO DTO содержащий данные сообщения.
     * @return DTO созданного сообщения или ошибку.
     */
    @PostMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Создание нового сообщения",
            description = "Позволяет создать новое сообщение, если отправитель и получатель являются друзьями"
    )
    public ResponseEntity<?> createMessage(@RequestBody MessageDTO messageDTO) {
        try {
            User sender = userService.getUserById(messageDTO.getSenderId());
            User receiver = userService.getUserById(messageDTO.getReceiverId());

            // Проверка, что пользователи являются друзьями.
            try {
                userFriendService.findByUserAndFriend(sender, receiver);
            } catch (UserFriendNotFoundException unfEx) {
                throw new MessageCreateException("Пользователи: " + sender + " и " + receiver + " не являются друзьями.");
            }

            messageDTO = messageService.createMessage(messageDTO);
            return ResponseEntity.status(HttpStatus.OK).body(messageDTO);

        } catch (MessageCreateException mcEx) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), mcEx.getMessage()));
        } catch (UserNotFoundException unfEx) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), unfEx.getMessage()));
        }
    }

    /**
     * API-endpoint для редактирования сообщения.
     * @param id идентификатор редактируемого сообщения.
     * @param messageEditDTO отредактированный контент сообщения.
     * @return DTO измененного сообщения.
     */
    @PutMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Редактирование сообщения",
            description = "Позволяет отредактировать сообщение отправленное пользователю"
    )
    public ResponseEntity<?> editMessage(@PathVariable Long id, @RequestBody MessageEditDTO messageEditDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(messageService.editMessage(id, messageEditDTO.getContent()));
        } catch (MessageNotFoundException mnfEx) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), mnfEx.getMessage()));
        } catch (AuthException authEx) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(HttpStatus.FORBIDDEN.value(), authEx.getMessage()));
        }
    }

    /**
     * API-endpoint для удаления сообщения.
     * @param id идентификатор сообщения.
     * @return ответ на запрос или ошибку.
     */
    @DeleteMapping("{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Удаление сообщения",
            description = "Позволят удалить сообщение авторизованному пользователю"
    )
    public ResponseEntity<?> deleteMessage(@PathVariable Long id) {
        try {
            messageService.deleteMessage(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (MessageNotFoundException mnfEx) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), mnfEx.getMessage()));
        } catch (AuthException authEx) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(HttpStatus.FORBIDDEN.value(), authEx.getMessage()));
        }
    }

    /**
     * API-endpoint для получения списка собеседников авторизованного пользователя.
     * @return список объектов UserDTO с данными пользователей или ошибку.
     */
    @GetMapping("/conversations")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение списка собеседников",
            description = "Позволяет получить список собеседников пользователя в порядке убывания даты последних сообщений"
    )
    public ResponseEntity<?> getConversations() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(messageService.getConversationsForUser());
        } catch (AuthException authEx) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(HttpStatus.FORBIDDEN.value(), authEx.getMessage()));
        }
    }

    /**
     * API-endpoint для получения переписки между пользователями.
     * @param id идентификатор собеседника.
     * @return список объектов MessageDTO с данными сообщений или ошибку.
     */
    @GetMapping("/conversations/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение переписки",
            description = "Позволяет получить переписку текущего пользователя с указанным собеседником"
    )
    public ResponseEntity<?> getMessages(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(messageService.getMessagesBetweenUsers(id));
        } catch (UserNotFoundException unfEx) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), unfEx.getMessage()));
        } catch (AuthException authEx) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(HttpStatus.FORBIDDEN.value(), authEx.getMessage()));
        }
    }
}
