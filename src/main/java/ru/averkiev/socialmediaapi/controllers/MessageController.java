package ru.averkiev.socialmediaapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.averkiev.socialmediaapi.models.MessageDTO;
import ru.averkiev.socialmediaapi.models.MessageEditDTO;
import ru.averkiev.socialmediaapi.models.User;
import ru.averkiev.socialmediaapi.models.UserDTO;
import ru.averkiev.socialmediaapi.services.impl.MessageServiceImpl;
import ru.averkiev.socialmediaapi.services.impl.UserFriendServiceImpl;
import ru.averkiev.socialmediaapi.services.impl.UserServiceImpl;

import java.util.List;

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
     * @return DTO созданного сообщения.
     */
    @PostMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Создание нового сообщения",
            description = "Позволяет создать новое сообщение, если отправитель и получатель являются друзьями"
    )
    public ResponseEntity<MessageDTO> createMessage(@RequestBody MessageDTO messageDTO) {

        User sender = userService.getUserById(messageDTO.getSenderId());
        User receiver = userService.getUserById(messageDTO.getReceiverId());

        // Проверка, что пользователи являются друзьями.
        userFriendService.findByUserAndFriend(sender, receiver);

        messageDTO = messageService.createMessage(messageDTO);
        return ResponseEntity.status(HttpStatus.OK).body(messageDTO);
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
    public ResponseEntity<MessageDTO> editMessage(@PathVariable Long id, @RequestBody MessageEditDTO messageEditDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.editMessage(id, messageEditDTO.getContent()));
    }

    /**
     * API-endpoint для удаления сообщения.
     * @param id идентификатор сообщения.
     * @return HTTP статус запроса.
     */
    @DeleteMapping("{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Удаление сообщения",
            description = "Позволят удалить сообщение авторизованному пользователю"
    )
    public ResponseEntity<HttpStatus> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * API-endpoint для получения списка собеседников авторизованного пользователя.
     * @return список объектов UserDTO с данными пользователей.
     */
    @GetMapping("/conversations")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение списка собеседников",
            description = "Позволяет получить список собеседников пользователя в порядке убывания даты последних сообщений"
    )
    public ResponseEntity<List<UserDTO>> getConversations() {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getConversationsForUser());
    }

    /**
     * API-endpoint для получения переписки между пользователями.
     * @param id идентификатор собеседника.
     * @return список объектов MessageDTO с данными сообщений.
     */
    @GetMapping("/conversations/{id}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение переписки",
            description = "Позволяет получить переписку текущего пользователя с указанным собеседником"
    )
    public ResponseEntity<List<MessageDTO>> getMessages(@PathVariable Long id) {
            return ResponseEntity.status(HttpStatus.OK).body(messageService.getMessagesBetweenUsers(id));
    }
}
