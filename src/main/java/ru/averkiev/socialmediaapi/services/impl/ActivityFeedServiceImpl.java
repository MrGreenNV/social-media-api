package ru.averkiev.socialmediaapi.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.averkiev.socialmediaapi.exceptions.ActivityFeedException;
import ru.averkiev.socialmediaapi.exceptions.AuthException;
import ru.averkiev.socialmediaapi.models.Post;
import ru.averkiev.socialmediaapi.models.PostDTO;
import ru.averkiev.socialmediaapi.models.Subscriber;
import ru.averkiev.socialmediaapi.models.User;
import ru.averkiev.socialmediaapi.services.ActivityFeedService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс представляет собой сервис реализующий функционал для управления отображением пользователю ленты активности.
 * @author mrGreenNV
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityFeedServiceImpl implements ActivityFeedService {

    /** Сервис для взаимодействия с аутентификацией пользователя. */
    private final AuthServiceImpl authService;

    /** Сервис для взаимодействия с пользователями. */
    private final UserServiceImpl userService;

    /** Сервис для взаимодействия с подписками пользователя. */
    private final SubscriberServiceImpl subscriberService;

    /** Сервис для взаимодействия с постами пользователей. */
    private final PostServiceImpl postService;

    /**
     * Позволяет получить ленту активности для аутентифицированного пользователя.
     * @param page номер отображаемой страницы.
     * @param pageSize количество отображаемых постов на одной странице.
     * @return список PostDTO содержащих данные постов.
     * @throws AuthException выбрасывает, если возникает ошибка связанная с аутентификацией пользователя.
     */
    @Override
    public List<PostDTO> getActivityFeedForUser(int page, int pageSize) throws AuthException {

        // Получение пользователя из аутентификации.
        User user = userService.getUserById(authService.getUserIdFromAuthentication());
        // Получение списка подписок пользователя.
        List<Subscriber> subscribers = subscriberService.findAllByUser(user);

        // Если нет подписок - выброс исключения.
        if (subscribers.isEmpty()) {
            log.error("IN getActivityFeedForUser - нет возможности отобразить ленту активности для пользователя: {}", user.getUsername());
            throw new ActivityFeedException("Пользователь не имеет подписок");
        }

        // Получение постов исходя из подписок пользователя.
        List<Post> posts = postService.getPostByUserIds(
                subscribers.stream()
                    .map(subscriber -> subscriber.getUser().getId())
                    .collect(Collectors.toList()),
                PageRequest.of(page, pageSize)
        );

        List<PostDTO> postDTOs = new ArrayList<>();

        // Преобразование объекта к DTO для передачи пользователю.
        for (Post post: posts) {
            PostDTO postDTO = new PostDTO();
            postDTO.setTitle(post.getTitle());
            postDTO.setText(post.getText());
            postDTO.setImages(post.getImages());
            postDTO.setUsername(post.getUser().getUsername());
        }

        log.info("IN getActivityFeedForUser - лента активности для пользователя: {} успешно получена", user.getUsername());
        return postDTOs;
    }
}
