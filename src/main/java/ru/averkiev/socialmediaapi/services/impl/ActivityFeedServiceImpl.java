package ru.averkiev.socialmediaapi.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.averkiev.socialmediaapi.exceptions.ActivityFeedException;
import ru.averkiev.socialmediaapi.exceptions.AuthException;
import ru.averkiev.socialmediaapi.models.*;
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
    private final SubscriptionServiceImpl subscriptionService;

    /** Сервис для взаимодействия с постами пользователей. */
    private final PostServiceImpl postService;

    /**
     * Позволяет получить ленту активности для аутентифицированного пользователя с пагинацией страниц.
     * @param page номер отображаемой страницы.
     * @param pageSize количество отображаемых постов на одной странице.
     * @return список PostDTO содержащих данные постов.
     * @throws AuthException выбрасывает, если возникает ошибка связанная с аутентификацией пользователя.
     */
    @Override
    public List<PostDTO> getActivityFeedForUser(Integer page, Integer pageSize) throws AuthException {

        // Получение пользователя из аутентификации.
        User user = userService.getUserById(authService.getUserIdFromAuthentication());
        // Получение списка подписок пользователя.
        List<Subscription> subscriptions = subscriptionService.findAllByUser(user);

        // Если нет подписок - выброс исключения.
        if (subscriptions.isEmpty()) {
            log.error("IN getActivityFeedForUser - нет возможности отобразить ленту активности для пользователя: {}", user.getUsername());
            throw new ActivityFeedException("Пользователь не имеет подписок");
        }

        // Получение постов исходя из подписок пользователя.
        List<Post> posts = postService.getPostByUserIds(
                subscriptions.stream()
                    .map(subscriber -> subscriber.getUser().getId())
                    .collect(Collectors.toList()),
                PageRequest.of(page, pageSize)
        );

        List<PostDTO> postDTOs = getPostDTOList(posts);

        log.info("IN getActivityFeedForUser - лента активности для пользователя: {} успешно получена", user.getUsername());
        return postDTOs;
    }

    /**
     * Позволяет получить ленту активности для аутентифицированного пользователя.
     * @return список PostDTO содержащих данные постов.
     * @throws AuthException выбрасывает, если возникает ошибка связанная с аутентификацией пользователя.
     */
    @Override
    public List<PostDTO> getActivityFeedForUser() throws AuthException {

        // Получение пользователя из аутентификации.
        User user = userService.getUserById(authService.getUserIdFromAuthentication());
        // Получение списка подписок пользователя.
        List<Subscription> subscriptions = subscriptionService.findAllByUser(user);

        // Если нет подписок - выброс исключения.
        if (subscriptions.isEmpty()) {
            log.error("IN getActivityFeedForUser - нет возможности отобразить ленту активности для пользователя: {}", user.getUsername());
            throw new ActivityFeedException("Пользователь не имеет подписок");
        }

        // Получение постов исходя из подписок пользователя.
        List<Post> posts = postService.getPostByUserIds(
                subscriptions.stream()
                        .map(subscriber -> subscriber.getUser().getId())
                        .collect(Collectors.toList())
        );

        List<PostDTO> postDTOs = getPostDTOList(posts);

        log.info("IN getActivityFeedForUser - лента активности для пользователя: {} успешно получена", user.getUsername());
        return postDTOs;
    }

    /**
     * Преобразует список объектов Post к PostDTO.
     * @param posts список постов.
     * @return список объектов PostDTO.
     */
    private List<PostDTO> getPostDTOList(List<Post> posts) {
        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post: posts) {
            PostDTO postDTO = new PostDTO();
            postDTO.setTitle(post.getTitle());
            postDTO.setText(post.getText());
            postDTO.setImages(post.getImages());
            postDTO.setUsername(post.getUser().getUsername());
            postDTOs.add(postDTO);
        }
        return postDTOs;
    }
}
