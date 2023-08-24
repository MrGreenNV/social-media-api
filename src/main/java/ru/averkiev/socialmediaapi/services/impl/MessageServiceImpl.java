package ru.averkiev.socialmediaapi.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.averkiev.socialmediaapi.exceptions.AuthException;
import ru.averkiev.socialmediaapi.exceptions.MessageNotFoundException;
import ru.averkiev.socialmediaapi.exceptions.UserNotFoundException;
import ru.averkiev.socialmediaapi.models.Message;
import ru.averkiev.socialmediaapi.models.MessageDTO;
import ru.averkiev.socialmediaapi.models.User;
import ru.averkiev.socialmediaapi.models.UserDTO;
import ru.averkiev.socialmediaapi.repositories.MessageRepository;
import ru.averkiev.socialmediaapi.services.MessageService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс реализует функциональность для сообщений пользователей, таких как создание, редактирование, удаление и
 * получение переписки.
 * @author mrGreenNV
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    /** Репозиторий для взаимодействия с базой данных. */
    private final MessageRepository messageRepository;

    /** Сервис для взаимодействия с пользователями. */
    private final UserServiceImpl userService;

    /** Сервис для взаимодействия с аутентификацией пользователей. */
    private final AuthServiceImpl authService;

    /**
     * Позволяет создать сообщение.
     * @param messageDTO DTO сообщения с данными о пользователях и контенте.
     * @return DTO созданного сообщения.
     * @throws UserNotFoundException выбрасывает, если не удалось найти пользователя в базе данных.
     */
    @Override
    public MessageDTO createMessage(MessageDTO messageDTO) throws UserNotFoundException {
        // Создание нового объекта сообщения.
        Message message = new Message();
        message.setSender(userService.getUserById(messageDTO.getSenderId()));
        message.setReceiver(userService.getUserById(messageDTO.getReceiverId()));
        message.setContent(messageDTO.getContent());

        // Сохранение сообщения в базе данных.
        messageDTO.setContent(messageRepository.save(message).getContent());
        log.info("IN createMessage - сообщение от пользователя: " + message.getSender().getUsername() + " к пользователю " + message.getReceiver().getUsername() + " успешно создано");
        return messageDTO;
    }

    /**
     * Позволяет отредактировать текст сообщения.
     * @param messageId идентификатор сообщения.
     * @param editContent отредактированный контент сообщения.
     * @return DTO отредактированного сообщения.
     * @throws MessageNotFoundException выбрасывает, если сообщение не найдено.
     * @throws AuthException выбрасывает, если недостаточно прав для редактирования сообщения.
     */
    @Override
    public MessageDTO editMessage(Long messageId, String editContent) throws MessageNotFoundException, AuthException {

        Message message = messageRepository.findById(messageId).orElse(null);
        if (message == null) {
            log.error("IN editMessage - сообщение с идентификатором: {} не изменено", messageId);
            throw new MessageNotFoundException("Сообщение с идентификатором: " + messageId + " не найдено");
        }

        if (!authService.getUserIdFromAuthentication().equals(message.getSender().getId())) {
            log.info("IN editMessage - сообщение с идентификатором: {} не изменено", messageId);
            throw new AuthException("Недостаточно прав для редактирования сообщения.");
        }

        message.setContent(editContent);
        message = messageRepository.save(message);

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(message.getId());
        messageDTO.setSenderId(message.getSender().getId());
        messageDTO.setReceiverId(message.getReceiver().getId());
        messageDTO.setContent(message.getContent());
        log.info("IN editMessage - сообщение с идентификатором: {} успешно изменено", message.getId());
        return messageDTO;
    }

    /**
     * Позволяет удалить сообщение между пользователями.
     * @param messageId идентификатор удаляемого сообщения.
     * @throws MessageNotFoundException выбрасывает, если сообщение не найдено.
     * @throws AuthException выбрасывает, если недостаточно прав для удаления сообщения.
     */
    @Override
    public void deleteMessage(Long messageId) throws MessageNotFoundException, AuthException {

        Message message = messageRepository.findById(messageId).orElse(null);
        if (message == null) {
            log.error("IN deleteMessage - сообщение с идентификатором: {} не удалено", messageId);
            throw new MessageNotFoundException("Сообщение с идентификатором: " + messageId + " не найдено");
        }

        if (!authService.getUserIdFromAuthentication().equals(message.getSender().getId())) {
            log.error("IN deleteMessage - сообщение с идентификатором: {} не удалено", messageId);
            throw new AuthException("Недостаточно прав для удаления сообщения");
        }

        messageRepository.deleteById(messageId);
        log.info("IN deleteMessage - сообщение с идентификатором: {} успешно удалено", messageId);
    }

    /**
     * Позволяет получить переписку между авторизованным пользователем и конкретным собеседником.
     * @param interlocutorId идентификатор собеседника.
     * @return список DTO объектом содержащий сообщения между пользователями.
     * @throws UserNotFoundException выбрасывает, если пользователь не найден.
     * @throws AuthException выбрасывает, если возникнет ошибка аутентификации пользователя в системе.
     */
    @Override
    public List<MessageDTO> getMessagesBetweenUsers(Long interlocutorId) throws UserNotFoundException, AuthException {

        User user = userService.getUserById(authService.getUserIdFromAuthentication());
        User interlocutor = userService.getUserById(interlocutorId);

        List<Message> messagesByUser = messageRepository.findAllBySenderAndReceiverOrReceiverAndSenderOrderByCreatedAt(
                user, interlocutor, user, interlocutor
        );

        List<MessageDTO> messageDTOList = new ArrayList<>();

        for (Message message : messagesByUser) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setId(message.getId());
            messageDTO.setSenderId(message.getSender().getId());
            messageDTO.setReceiverId(message.getReceiver().getId());
            messageDTO.setContent(message.getContent());

            messageDTOList.add(messageDTO);
        }

        log.info("IN getMessagesBetweenUsers - переписка пользователя: {} с собеседником: {} успешно получена", user.getUsername(), interlocutor.getUsername());
        return messageDTOList;
    }

    /**
     * Позволяет получить список собеседников пользователя.
     * @return список DTO объектов содержащих данные на собеседников.
     * @throws AuthException выбрасывает если при аутентификации пользователя возникает ошибка.
     */
    @Override
    public List<UserDTO> getConversationsForUser() throws AuthException {

        User user = userService.getUserById(authService.getUserIdFromAuthentication());

        List<UserDTO> conversations = new ArrayList<>();

        // Получаем список сообщений, отправленных пользователем.
        List<Message> sentMessages = messageRepository.findAllBySenderOrderByCreatedAtDesc(userService.getUserById(user.getId()));
        // Получаем список сообщений, полученных пользователем.
        List<Message> receivedMessages = messageRepository.findAllByReceiverOrderByCreatedAtDesc(userService.getUserById(user.getId()));

        Map<User, LocalDateTime> lastMessageByUser = new HashMap<>();

        // Для каждого отправленного сообщения получаем получателя и если он отсутствует в мапе или у данного сообщения дата меньше - сохраняем в мапу.
        for (Message message: sentMessages) {
            User receiver = message.getReceiver();
            if (!lastMessageByUser.containsKey(receiver) || message.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).isAfter(lastMessageByUser.get(receiver).atZone(ZoneId.systemDefault()))) {
                lastMessageByUser.put(receiver, message.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            }
        }
        // Для каждого полученного сообщения получаем отправителя и если он отсутствует в мапе или у данного сообщения дата меньше - сохраняем в мапу.
        for (Message message: receivedMessages) {
            User sender = message.getSender();
            if (!lastMessageByUser.containsKey(sender) || message.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).isAfter(lastMessageByUser.get(sender).atZone(ZoneId.systemDefault()))) {
                lastMessageByUser.put(sender, message.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            }
        }

        for (User usr : lastMessageByUser.keySet()) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(usr.getId());
            userDTO.setUsername(usr.getUsername());

            conversations.add(userDTO);
        }
        log.info("IN getConversationsForUser - список собеседников пользователя: {} успешно получен", user.getUsername());
        return conversations;
    }
}
