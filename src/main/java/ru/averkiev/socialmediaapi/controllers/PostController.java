package ru.averkiev.socialmediaapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.averkiev.socialmediaapi.exceptions.AuthException;
import ru.averkiev.socialmediaapi.exceptions.ImageNotFoundException;
import ru.averkiev.socialmediaapi.exceptions.PostCreationException;
import ru.averkiev.socialmediaapi.exceptions.PostNotFoundException;
import ru.averkiev.socialmediaapi.models.Image;
import ru.averkiev.socialmediaapi.models.Post;
import ru.averkiev.socialmediaapi.services.impl.PostServiceImpl;
import ru.averkiev.socialmediaapi.utils.ErrorResponse;

import java.util.List;

/**
 * Класс представляет собой REST-контроллер для взаимодействия с постами пользователей.
 * Этот класс предоставляет API-endpoints для выполнения операций создания и обновления постов, а также добавления и удаления к ним изображений.
 * Все запросы выполняются в формате JSON.
 * @author mrGreenNV
 */
@RestController
@RequestMapping("/social-media-api/posts")
@RequiredArgsConstructor
@Tag(
        name = "PostController",
        description = "Позволяет управлять постами пользователей"
)
public class PostController {

    /** Сервис для взаимодействия с постами. */
    private final PostServiceImpl postService;

    /**
     * API-endpoint для создания нового поста.
     * @param post объект Post содержащий данные для создания поста
     * @return объект Post если создание прошло успешно, иначе - ошибку.
     */
    @PostMapping
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Создание нового поста",
            description = "Позволяет создать новый пост по данным полученным из тела запроса"
    )
    public ResponseEntity<?> createPost(@RequestBody Post post) {
        try {
            return ResponseEntity.ok(postService.createPost(post));
        } catch (PostCreationException pnfEx) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), pnfEx.getMessage()));
        } catch (AuthException aEx) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(HttpStatus.FORBIDDEN.value(), aEx.getMessage()));
        }
    }

    /**
     * API-endpoint для обновления поста в базе данных.
     * @param postId идентификатор обновляемого поста.
     * @param updatePost объект Post, содержащий обновленные данные.
     * @return объект Post с обновленными данными, если обновление прошло успешно, иначе - ошибку.
     */
    @PutMapping("/{postId}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Обновление существующего поста",
            description = "Позволяет обновить существующий пост в базе данных"
    )
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody Post updatePost) {
        try {
            return ResponseEntity.ok(postService.updatePost(postId, updatePost));
        } catch (PostNotFoundException pnfEx) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), pnfEx.getMessage()));
        } catch (AuthException aEx) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(HttpStatus.FORBIDDEN.value(), aEx.getMessage()));
        }
    }

    /**
     * API-endpoint для удаления поста из базы данных.
     * @param postId идентификатор удаляемого поста.
     * @return статус ответа или ошибку, если не удалось удалить пост.
     */
    @DeleteMapping("/{postId}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Удаление поста",
            description = "Позволяет удалить пост из базы данных, при этом удаляются связанные с ним изображения"
    )
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        try {
            postService.deletePost(postId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (PostNotFoundException pnfEx) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), pnfEx.getMessage()));
        } catch (AuthException aEx) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(HttpStatus.FORBIDDEN.value(), aEx.getMessage()));
        }
    }

    /**
     * API-endpoint для прикрепления изображения к посту.
     * @param postId идентификатор поста, к которому прикрепляется изображение.
     * @param image прикрепляемое изображение.
     * @return статус ответа или ошибку, если не удалось прикрепить изображение.
     */
    @PostMapping("/{postId}/images")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Прикрепление изображения к посту",
            description = "Позволяет прикрепить изображение к посту, сохраняя массив байт в базе данных"
    )
    public ResponseEntity<?> addImageToPost(@PathVariable Long postId, @RequestBody Image image) {
        try {
            postService.addImage(postId, image);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (PostNotFoundException pnfEx) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), pnfEx.getMessage()));
        } catch (AuthException aEx) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(HttpStatus.FORBIDDEN.value(), aEx.getMessage()));
        }
    }

    /**
     * API-endpoint для открепления изображения от поста.
     * @param postID идентификатор поста
     * @param imageId идентификатор изображения
     * @return статус ответа или ошибку, если не удалось открепить изображение.
     */
    @DeleteMapping("/{postID}/images/{imageId}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Открепление изображение от поста",
            description = "Позволяет открепить изображение от поста, удаляя его из базы данных"
    )
    public ResponseEntity<?> deleteImage(@PathVariable Long postID, @PathVariable Long imageId) {
        try {
            postService.deleteImage(postID, imageId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (PostNotFoundException | ImageNotFoundException nfEx) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), nfEx.getMessage()));
        } catch (AuthException aEx) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(HttpStatus.FORBIDDEN.value(), aEx.getMessage()));
        }
    }

    /**
     * API-endpoint для вывода списка постов аутентифицированного пользователя.
     * @return список постов или ошибку, если аутентификация не была пройдена.
     */
    @GetMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение списка постов",
            description = "Позволяет получить список постов для текущего пользователя"
    )
    public ResponseEntity<?> getAllPostsByUser() {
        try {
            List<Post> posts = postService.showAllPostsByUser();
            return ResponseEntity.status(HttpStatus.OK).body(posts);
        } catch (AuthException authEx) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(HttpStatus.FORBIDDEN.value(), authEx.getMessage()));
        }
    }
}
