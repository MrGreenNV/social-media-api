package ru.averkiev.socialmediaapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.socialmediaapi.models.FriendshipRequest;
import ru.averkiev.socialmediaapi.models.User;

import java.util.Optional;

/**
 * Интерфейс представляет собой функциональность взаимодействия объекта FriendshipRequest с базой данных.
 * @author mrGreenNV
 */
@Repository
public interface FriendshipRequestRepository extends JpaRepository<FriendshipRequest, Long> {
    Optional<FriendshipRequest> findFriendshipRequestByFromUserAndToUser(User fromUser, User toUser);
}
