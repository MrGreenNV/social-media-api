package ru.averkiev.socialmediaapi.services;

import ru.averkiev.socialmediaapi.exceptions.AuthException;
import ru.averkiev.socialmediaapi.exceptions.MessageNotFoundException;
import ru.averkiev.socialmediaapi.exceptions.UserNotFoundException;
import ru.averkiev.socialmediaapi.models.MessageDTO;
import ru.averkiev.socialmediaapi.models.UserDTO;

import java.util.List;

/**
 * Интерфейс определяет функциональность для сообщений пользователей, таких как создание, редактирование, удаление и
 * получение переписки.
 * @author mrGreenNV
 */
public interface MessageService {

    /**
     * Позволяет создать сообщение.
     * @param messageDTO DTO сообщения с данными о пользователях и контенте.
     * @return DTO созданного сообщения.
     * @throws UserNotFoundException выбрасывает, если не удалось найти пользователя в базе данных.
     */
    MessageDTO createMessage(MessageDTO messageDTO) throws UserNotFoundException;

    /**
     * Позволяет отредактировать текст сообщения.
     * @param editMessageDTO DTO сообщения с отредактированным текстом.
     * @return DTO отредактированного сообщения.
     * @throws MessageNotFoundException выбрасывает, если сообщение не найдено.
     */
    MessageDTO editMessage(MessageDTO editMessageDTO) throws MessageNotFoundException;

    /**
     * Позволяет удалить сообщение между пользователями.
     * @param messageDTO DTO сообщения с данными о пользователях и контенте.
     * @throws MessageNotFoundException выбрасывает, если сообщение не найдено.
     */
    void deleteMessage(MessageDTO messageDTO) throws MessageNotFoundException;

    /**
     * Позволяет получить переписку между авторизованным пользователем и конкретным собеседником.
     * @param interlocutorId идентификатор собеседника.
     * @return список DTO объектом содержащий сообщения между пользователями.
     * @throws UserNotFoundException выбрасывает, если пользователь не найден.
     * @throws AuthException выбрасывает, если возникнет ошибка аутентификации пользователя в системе.
     */
    List<MessageDTO> getMessagesBetweenUsers(Long interlocutorId) throws UserNotFoundException, AuthException;

    /**
     * Позволяет получить список собеседников пользователя.
     * @return список DTO объектов содержащих данные на собеседников.
     */
    List<UserDTO> getConversationsForUser();
}
