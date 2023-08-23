package ru.averkiev.socialmediaapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.averkiev.socialmediaapi.exceptions.*;
import ru.averkiev.socialmediaapi.models.FriendshipRequest;
import ru.averkiev.socialmediaapi.models.FriendshipRequestDTO;
import ru.averkiev.socialmediaapi.models.User;
import ru.averkiev.socialmediaapi.models.UserFriendDTO;
import ru.averkiev.socialmediaapi.services.impl.AuthServiceImpl;
import ru.averkiev.socialmediaapi.services.impl.FriendshipRequestServiceImpl;
import ru.averkiev.socialmediaapi.services.impl.UserServiceImpl;
import ru.averkiev.socialmediaapi.utils.ErrorResponse;

import java.util.List;

/**
 * Класс представляет собой REST-контроллер для взаимодействия с пользователями в системе.
 * Этот класс предоставляет API-endpoints для выполнения взаимодействия между пользователями, включая отправление
 * запросов на дружбу, их принятие или отклонение, а также управление подписками пользователей.
 * Все запросы выполняются в формате JSON.
 * @author mrGreenNV
 */
@RestController
@RequestMapping("/friendship-requests")
@RequiredArgsConstructor
@Tag(
        name = "FriendshipRequestController",
        description = "Позволяет управлять взаимодействием между пользователями"
)
public class FriendshipRequestController {

    /** Сервис для взаимодействия с пользователями. */
    private final UserServiceImpl userService;

    /** Сервис для взаимодействия с заявками на дружбу */
    private final FriendshipRequestServiceImpl friendshipRequestService;

    /** Сервис для взаимодействия с аутентификацией пользователей. */
    private final AuthServiceImpl authService;

    /**
     * API-endpoint для получения всех ожидающих запросов на дружбу.
     * @return статус запроса или сообщение об ошибке.
     */
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение списка ожидающих запросов на дружбу",
            description = "Выводит список ожидающих запросов на дружбу для аутентифицированного пользователя."
    )
    @GetMapping("/pending")
    public ResponseEntity<?> getPendingFriendshipRequest() {
        try {
            User user = userService.getUserById(authService.getUserIdFromAuthentication());
            List<FriendshipRequest> pendingRequest = friendshipRequestService.getReceivedPendingFriendshipRequests(user);
            return ResponseEntity.ok(pendingRequest);
        } catch (AuthException authEx) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(HttpStatus.FORBIDDEN.value(), authEx.getMessage()));
        } catch (UserNotFoundException unfEx) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), unfEx.getMessage()));
        }
    }

    /**
     * API-endpoint для создания запроса дружбы.
     * @param requestDTO DTO запрос содержащий информацию об идентификаторах пользователей.
     * @return статус запроса или сообщение об ошибке.
     */
    @PostMapping("/send")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Отправка запроса на дружбу",
            description = "Создает запрос на дружбу и подписку"
    )
    public ResponseEntity<?> sendFriendRequest(@RequestBody FriendshipRequestDTO requestDTO) {
        try {
            User fromUser = userService.getUserById(requestDTO.getFromUser());
            User toUser = userService.getUserById(requestDTO.getToUser());

            friendshipRequestService.sendFriendRequest(fromUser, toUser);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (FriendshipRequestCreateException frcEx) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), frcEx.getMessage()));
        } catch (UserNotFoundException unfEx) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), unfEx.getMessage()));
        }
    }

    /**
     * API-endpoint для принятия заявки на дружбу.
     * @param requestDTO DTO запрос содержащий информацию об идентификаторах пользователей.
     * @return статус запроса или сообщение об ошибке.
     */
    @PostMapping("/accept")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Принятие запроса на дружбу",
            description = "Принимает запрос на дружбу и подписывается в ответ"
    )
    public ResponseEntity<?> acceptFriendRequest(@RequestBody FriendshipRequestDTO requestDTO) {
        try {
            User fromUser = userService.getUserById(requestDTO.getToUser());
            User toUser = userService.getUserById(requestDTO.getToUser());

            FriendshipRequest request = friendshipRequestService.findFriendshipRequest(fromUser, toUser);
            friendshipRequestService.acceptFriendshipRequest(request);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (FriendshipRequestNotFoundException | UserNotFoundException nfEx) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), nfEx.getMessage()));
        }
    }

    /**
     * API-endpoint для отклонения заявки на дружбу.
     * @param requestDTO DTO запрос содержащий информацию об идентификаторах пользователей.
     * @return статус запроса или сообщение об ошибке.
     */
    @PostMapping("/decline")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Отклонение запроса на дружбу",
            description = "Отклоняет запрос на дружбу и удаляет его из базы данных"
    )
    public ResponseEntity<?> declineFriendRequest(@RequestBody FriendshipRequestDTO requestDTO) {
        try {
            User fromUser = userService.getUserById(requestDTO.getToUser());
            User toUser = userService.getUserById(requestDTO.getToUser());

            FriendshipRequest request = friendshipRequestService.findFriendshipRequest(fromUser, toUser);
            friendshipRequestService.acceptFriendshipRequest(request);

            friendshipRequestService.declineFriendshipRequest(request);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (FriendshipRequestNotFoundException | UserNotFoundException nfEx) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), nfEx.getMessage()));
        }
    }

    /**
     * API-endpoint для удаления дружеской связи.
     * @param userFriendDTO DTO дружеской связи содержащей идентификаторы пользователей.
     * @return статус запроса или сообщение об ошибке.
     */
    @PostMapping("/delete")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Удаление дружеской связи между пользователями",
            description = "Удаляет дружескую связь, запрос на дружбу и отписывается от удаляемого пользователя"
    )
    public ResponseEntity<?> deleteFriendship(@RequestBody UserFriendDTO userFriendDTO) {
        try {
            User fromUser = userService.getUserByUsername(userFriendDTO.getUsername());
            User toUser = userService.getUserByUsername(userFriendDTO.getUsername());

            friendshipRequestService.cancelFriendshipRequest(fromUser, toUser);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (FriendshipRequestNotFoundException | UserFriendNotFoundException | SubscriberNotFoundException | SubscriptionNotFoundException nfEx) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), nfEx.getMessage()));
        }
    }

}
