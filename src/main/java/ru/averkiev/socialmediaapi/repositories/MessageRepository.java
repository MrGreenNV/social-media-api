package ru.averkiev.socialmediaapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.socialmediaapi.models.Message;
import ru.averkiev.socialmediaapi.models.User;

import java.util.List;

/**
 * Интерфейс представляет собой функциональность взаимодействия объекта Message с базой данных.
 * @author mrGreenNV
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Выполняет поиск всех сообщений пользователя с собеседником.
     * @param user пользователь.
     * @param interlocutor собеседник.
     * @return список объектов Message с данными сообщений.
     */
    List<Message> findAllBySenderAndReceiverOrderByCreatedAt(User user, User interlocutor);

    /**
     * Выполняет поиск сообщений в которых пользователь является отправителем и сортирует по дате в порядке убывания.
     * @param sender отправитель.
     * @return список объектов Message с данными сообщений.
     */
    List<Message> findAllBySenderOrderByCreatedAtDesc(User sender);

    /**
     * Выполняет поиск сообщений в которых пользователь является получателем и сортирует по дате в порядке убывания.
     * @param receiver получатель.
     * @return список объектов Message с данными сообщений.
     */
    List<Message> findAllByReceiverOrderByCreatedAtDesc(User receiver);
}
