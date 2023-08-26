package ru.averkiev.socialmediaapi.services;

import ru.averkiev.socialmediaapi.exceptions.AuthException;
import ru.averkiev.socialmediaapi.models.PostDTO;

import java.util.List;

/**
 * Интерфейс предоставляет функционал для отображения ленты активности пользователю.
 * @author mrGreenNV
 */
public interface ActivityFeedService {

    /**
     * Позволяет получить ленту активности для аутентифицированного пользователя с пагинацией страниц.
     * @param Page номер отображаемой страницы.
     * @param pageSize количество отображаемых постов на одной странице.
     * @return список PostDTO содержащих данные постов.
     * @throws AuthException выбрасывает, если возникает ошибка связанная с аутентификацией пользователя.
     */
    List<PostDTO> getActivityFeedForUser(Integer Page, Integer pageSize) throws AuthException;

    /**
     * Позволяет получить ленту активности для аутентифицированного пользователя.
     * @return список PostDTO содержащих данные постов.
     * @throws AuthException выбрасывает, если возникает ошибка связанная с аутентификацией пользователя.
     */
    List<PostDTO> getActivityFeedForUser() throws AuthException;
}
