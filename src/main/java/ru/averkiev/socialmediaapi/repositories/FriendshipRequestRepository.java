package ru.averkiev.socialmediaapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.socialmediaapi.models.FriendshipRequest;
import ru.averkiev.socialmediaapi.models.FriendshipRequestStatus;
import ru.averkiev.socialmediaapi.models.User;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс представляет собой функциональность взаимодействия объекта FriendshipRequest с базой данных.
 * @author mrGreenNV
 */
@Repository
public interface FriendshipRequestRepository extends JpaRepository<FriendshipRequest, Long> {

    /**
     * Выполняет поиск заявки на дружбу в базе данных.
     * @param fromUser пользователь, отправивший заявку.
     * @param toUser пользователь, заявка которому предназначается.
     * @return Optional, содержащий заявку, если её удалось найти, иначе пустой.
     */
    Optional<FriendshipRequest> findFriendshipRequestByFromUserAndToUser(User fromUser, User toUser);

    /**
     * Выполняет поиск всех заявок с определенным статусов для пользователя, отправившего заявки.
     * @param fromUser пользователь, отправивший заявки.
     * @param status статус заявок.
     * @return список заявок.
     */
    List<FriendshipRequest> findAllByFromUserAndStatus(User fromUser, FriendshipRequestStatus status);

    /**
     * Выполняет поиск всех заявок с определенным статусов для пользователя, получившего заявки.
     * @param toUser пользователь, получивший заявки.
     * @param status статус заявок.
     * @return список заявок.
     */
    List<FriendshipRequest> findAllByToUserAndStatus(User toUser, FriendshipRequestStatus status);
}
