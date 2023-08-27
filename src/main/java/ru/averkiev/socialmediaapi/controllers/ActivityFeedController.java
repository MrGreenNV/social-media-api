package ru.averkiev.socialmediaapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.averkiev.socialmediaapi.models.PostDTO;
import ru.averkiev.socialmediaapi.services.impl.ActivityFeedServiceImpl;

import java.util.List;

/**
 * Класс представляет собой REST-контроллер отображения ленты активности для пользователей.
 * Этот класс предоставляет API-endpoints для взаимодействия с лентой активности.
 * Все запросы выполняются в формате JSON.
 * @author mrGreenNV
 */
@RestController
@RequestMapping("/social-media-api/activity")
@RequiredArgsConstructor
@Tag(
        name = "ActivityFeedController",
        description = "Позволяет отображать ленту активности для пользователей"
)
public class ActivityFeedController {

    /** Сервис для взаимодействия с лентой активности. */
    private final ActivityFeedServiceImpl activityFeedService;

    /**
     * API-endpoint для отображения ленты активности для пользователя.
     * @param page номер отображаемой страницы.
     * @param pageSize количество отображаемых постов на странице.
     * @return список объектов DTO для постов.
     */
    @GetMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Отображает ленту активности для пользователя",
            description = "Позволяет вывести список постов в соответствии с подписками пользователя"
    )
    public ResponseEntity<List<PostDTO>> getActivityFeedForUser(
            @Parameter(name = "page", description = "Номер отображаемой страницы") @RequestParam(value = "page", required = false) Integer page,
            @Parameter(name = "pageSize", description = "Количество отображаемых элементов на странице") @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        if (page == null || pageSize == null) {
            return ResponseEntity.status(HttpStatus.OK).body(activityFeedService.getActivityFeedForUser());
        }
        return ResponseEntity.status(HttpStatus.OK).body(activityFeedService.getActivityFeedForUser(page, pageSize));
    }
}
