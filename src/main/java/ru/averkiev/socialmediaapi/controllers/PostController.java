package ru.averkiev.socialmediaapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.averkiev.socialmediaapi.models.Image;
import ru.averkiev.socialmediaapi.models.Post;
import ru.averkiev.socialmediaapi.services.impl.PostServiceImpl;

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
     * @return объект Post.
     */
    @PostMapping
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Создание нового поста",
            description = "Позволяет создать новый пост по данным полученным из тела запроса"
    )
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        return ResponseEntity.ok(postService.createPost(post));
    }

    /**
     * API-endpoint для обновления поста в базе данных.
     * @param postId идентификатор обновляемого поста.
     * @param updatePost объект Post, содержащий обновленные данные.
     * @return объект Post с обновленными данными.
     */
    @PutMapping("/{postId}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Обновление существующего поста",
            description = "Позволяет обновить существующий пост в базе данных"
    )
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody Post updatePost) {
        return ResponseEntity.ok(postService.updatePost(postId, updatePost));
    }

    /**
     * API-endpoint для удаления поста из базы данных.
     * @param postId идентификатор удаляемого поста.
     * @return HTTP статус запроса.
     */
    @DeleteMapping("/{postId}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Удаление поста",
            description = "Позволяет удалить пост из базы данных, при этом удаляются связанные с ним изображения"
    )
    public ResponseEntity<HttpStatus> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * API-endpoint для прикрепления изображения к посту.
     * @param postId идентификатор поста, к которому прикрепляется изображение.
     * @param image прикрепляемое изображение.
     * @return HTTP статус запроса.
     */
    @PostMapping("/{postId}/images")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Прикрепление изображения к посту",
            description = "Позволяет прикрепить изображение к посту, сохраняя массив байт в базе данных"
    )
    public ResponseEntity<?> addImageToPost(@PathVariable Long postId, @RequestBody Image image) {
        postService.addImage(postId, image);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * API-endpoint для открепления изображения от поста.
     * @param postID идентификатор поста
     * @param imageId идентификатор изображения
     * @return HTTP статус запроса.
     */
    @DeleteMapping("/{postID}/images/{imageId}")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Открепление изображение от поста",
            description = "Позволяет открепить изображение от поста, удаляя его из базы данных"
    )
    public ResponseEntity<?> deleteImage(@PathVariable Long postID, @PathVariable Long imageId) {
        postService.deleteImage(postID, imageId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * API-endpoint для вывода списка постов аутентифицированного пользователя.
     * @param page номер отображаемой страницы.
     * @param pageSize количество отображаемых постов на странице.
     * @return список постов или ошибку, если аутентификация не была пройдена.
     */
    @GetMapping()
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение списка постов пользователя",
            description = "Позволяет получить список постов текущего пользователя с возможностью пагинации"
    )
    public ResponseEntity<List<Post>> getAllPostsByUser(@RequestParam(required = false) Integer page,
                                                        @RequestParam(required = false) Integer pageSize) {
        if (page == null || pageSize == null) {
            return ResponseEntity.status(HttpStatus.OK).body(postService.showAllPostsByUser());
        }
        return ResponseEntity.status(HttpStatus.OK).body(postService.showAllPostsByUser(PageRequest.of(page, pageSize)));
    }

    /**
     * API-endpoint для получения списка всех постов отсортированных по дате создания.
     * @param page номер отображаемой страницы.
     * @param pageSize количество отображаемых постов на странице.
     * @return список объектов Post.
     */
    @GetMapping("/all")
    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получения списка всех постов",
            description = "Позволяет получить список всех постов отсортированных по дате создания с возможностью пагинации"
    )
    public ResponseEntity<?> getAllPosts(@RequestParam(required = false) Integer page,
                                         @RequestParam(required = false) Integer pageSize) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPostByCreateAt(PageRequest.of(page, pageSize)));
    }
}
