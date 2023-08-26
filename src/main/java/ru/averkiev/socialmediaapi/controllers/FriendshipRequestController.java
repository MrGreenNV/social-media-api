package ru.averkiev.socialmediaapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.averkiev.socialmediaapi.models.FriendshipRequest;
import ru.averkiev.socialmediaapi.models.FriendshipRequestDTO;
import ru.averkiev.socialmediaapi.models.User;
import ru.averkiev.socialmediaapi.services.impl.AuthServiceImpl;
import ru.averkiev.socialmediaapi.services.impl.FriendshipRequestServiceImpl;
import ru.averkiev.socialmediaapi.services.impl.UserServiceImpl;

import java.util.List;

/**
 * Класс представляет собой REST-контроллер для взаимодействия с пользователями в системе.
 * Этот класс предоставляет API-endpoints для выполнения взаимодействия между пользователями, включая отправление
 * запросов на дружбу, их принятие или отклонение, а также управление подписками пользователей.
 * Все запросы выполняются в формате JSON.
 * @author mrGreenNV
 */
@RestController
@RequestMapping("/social-media-api/friendship-requests")
@RequiredArgsConstructor
@Tag(
        name = "FriendshipRequestController",
        description = "Позволяет управлять взаимодействием между пользователями"
)
public class FriendshipRequestController {

    /** Сервис для взаимодействия с пользователями. */
    private final UserServiceImpl userService;

    /** Сервис для взаимодействия с запросами на дружбу */
    private final FriendshipRequestServiceImpl friendshipRequestService;

    /** Сервис для взаимодействия с аутентификацией пользователей. */
    private final AuthServiceImpl authService;

    /**
     * API-endpoint для получения всех ожидающих запросов на дружбу.
     * @return список запросов на дружбу.
     */
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение списка ожидающих запросов на дружбу",
            description = "Выводит список ожидающих запросов на дружбу для аутентифицированного пользователя."
    )
    @GetMapping("/pending")
    public ResponseEntity<List<FriendshipRequest>> getPendingFriendshipRequest() {
        User user = userService.getUserById(authService.getUserIdFromAuthentication());
        List<FriendshipRequest> pendingRequest = friendshipRequestService.getReceivedPendingFriendshipRequests(user);
        return ResponseEntity.ok(pendingRequest);
    }

    /**
     * API-endpoint для создания запроса на дружбу.
     * @param requestDTO DTO запрос содержащий информацию об идентификаторах пользователей.
     * @return HTTP статус запроса.
     */
    @PostMapping("/send")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Отправка запроса на дружбу",
            description = "Создает запрос на дружбу и подписку"
    )
    public ResponseEntity<HttpStatus> sendFriendRequest(@RequestBody FriendshipRequestDTO requestDTO) {
        User fromUser = userService.getUserById(requestDTO.getFromUserId());
        User toUser = userService.getUserById(requestDTO.getToUserId());

        friendshipRequestService.sendFriendRequest(fromUser, toUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * API-endpoint для принятия запроса на дружбу.
     * @param requestDTO DTO запрос содержащий информацию об идентификаторах пользователей.
     * @return HTTP статус запроса.
     */
    @PostMapping("/accept")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Принятие запроса на дружбу",
            description = "Принимает запрос на дружбу и подписывается в ответ"
    )
    public ResponseEntity<HttpStatus> acceptFriendRequest(@RequestBody FriendshipRequestDTO requestDTO) {
            User fromUser = userService.getUserById(requestDTO.getFromUserId());
            User toUser = userService.getUserById(requestDTO.getToUserId());

            FriendshipRequest request = friendshipRequestService.findFriendshipRequest(fromUser, toUser);
            friendshipRequestService.acceptFriendshipRequest(request);

            return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * API-endpoint для отклонения запроса на дружбу.
     * @param requestDTO DTO запроса на дружбу, содержащий информацию об идентификаторах пользователей.
     * @return HTTP статус запроса.
     */
    @PostMapping("/decline")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Отклонение запроса на дружбу",
            description = "Отклоняет запрос на дружбу и удаляет его из базы данных"
    )
    public ResponseEntity<HttpStatus> declineFriendRequest(@RequestBody FriendshipRequestDTO requestDTO) {
        User fromUser = userService.getUserById(requestDTO.getFromUserId());
        User toUser = userService.getUserById(requestDTO.getToUserId());

        FriendshipRequest request = friendshipRequestService.findFriendshipRequest(fromUser, toUser);
        friendshipRequestService.acceptFriendshipRequest(request);

        friendshipRequestService.declineFriendshipRequest(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * API-endpoint для удаления дружеской связи.
     * @param requestDTO DTO запроса на дружбу, содержащий информацию об идентификаторах пользователей.
     * @return HTTP статус запроса.
     */
    @PostMapping("/delete")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Удаление дружеской связи между пользователями",
            description = "Удаляет дружескую связь, запрос на дружбу и отписывается от удаляемого пользователя"
    )
    public ResponseEntity<HttpStatus> deleteFriendship(@RequestBody FriendshipRequestDTO requestDTO) {
            User fromUser = userService.getUserById(requestDTO.getFromUserId());
            User toUser = userService.getUserById(requestDTO.getToUserId());

            friendshipRequestService.cancelFriendshipRequest(fromUser, toUser);
            return ResponseEntity.status(HttpStatus.OK).build();
    }

}