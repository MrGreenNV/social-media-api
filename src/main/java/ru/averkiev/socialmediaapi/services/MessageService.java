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
     * @param messageId идентификатор сообщения.
     * @param editContent отредактированный контент сообщения.
     * @return DTO отредактированного сообщения.
     * @throws MessageNotFoundException выбрасывает, если сообщение не найдено.
     * @throws AuthException выбрасывает, если недостаточно прав для редактирования сообщения.
     */
    MessageDTO editMessage(Long messageId, String editContent) throws MessageNotFoundException, AuthException;

    /**
     * Позволяет удалить сообщение между пользователями.
     * @param messageId идентификатор удаляемого сообщения.
     * @throws MessageNotFoundException выбрасывает, если сообщение не найдено.
     * @throws AuthException выбрасывает, если недостаточно прав для удаления сообщения.
     */
    void deleteMessage(Long messageId) throws MessageNotFoundException, AuthException;

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
     * @throws AuthException выбрасывает если при аутентификации пользователя возникает ошибка.
     */
    List<UserDTO> getConversationsForUser() throws AuthException;
}
