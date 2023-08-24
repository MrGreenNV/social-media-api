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
     * @param editMessageDTO DTO сообщения с отредактированным текстом.
     * @return DTO отредактированного сообщения.
     * @throws MessageNotFoundException выбрасывает, если сообщение не найдено.
     */
    @Override
    public MessageDTO editMessage(MessageDTO editMessageDTO) throws MessageNotFoundException {
        Message message = messageRepository.findById(editMessageDTO.getId()).orElse(null);
        if (message == null) {
            log.error("IN editMessage - сообщение с идентификатором: {} не изменено", editMessageDTO.getId());
            throw new MessageNotFoundException("Сообщение с идентификатором: " + editMessageDTO.getId() + " не найдено");
        }

        message.setContent(editMessageDTO.getContent());
        editMessageDTO.setContent(messageRepository.save(message).getContent());
        log.info("IN editMessage - сообщение с идентификатором: {} успешно изменено", message.getId());
        return editMessageDTO;
    }

    /**
     * Позволяет удалить сообщение между пользователями.
     * @param messageDTO DTO сообщения с данными о пользователях и контенте.
     * @throws MessageNotFoundException выбрасывает, если сообщение не найдено.
     */
    @Override
    public void deleteMessage(MessageDTO messageDTO) throws MessageNotFoundException {
        if (!messageRepository.existsById(messageDTO.getId())) {
            log.error("IN deleteMessage - сообщение с идентификатором: {} не удалено", messageDTO.getId());
            throw new MessageNotFoundException("Сообщение с идентификатором: " + messageDTO.getId() + " не найдено");
        }
        messageRepository.deleteById(messageDTO.getId());
        log.info("IN deleteMessage - сообщение с идентификатором: {} успешно удалено", messageDTO.getId());
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

        List<Message> messagesByUser = messageRepository.findAllBySenderAndReceiverOrderByCreatedAt(user, interlocutor);

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

    @Override
    public List<UserDTO> getConversationsForUser() {

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
        log.info("IN getConversationsForUser - список бесед пользователя: {} успешно получен", user.getUsername());
        return conversations;
    }
}
