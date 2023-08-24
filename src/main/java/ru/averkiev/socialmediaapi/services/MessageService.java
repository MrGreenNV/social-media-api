package ru.averkiev.socialmediaapi.services;

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
     * @param interlocutor собеседник.
     * @return список DTO объектом содержащий сообщения между пользователями.
     */
    List<MessageDTO> getMessagesBetweenUsers(Long interlocutor);

    /**
     * Позволяет получить список собеседников пользователя.
     * @param userDTO DTO пользователя.
     * @return список DTO объектов содержащих данные на собеседников.
     */
    List<UserDTO> getConversationsForUser(UserDTO userDTO);
}
