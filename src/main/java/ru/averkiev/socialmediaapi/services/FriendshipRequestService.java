package ru.averkiev.socialmediaapi.services;

import ru.averkiev.socialmediaapi.exceptions.*;
import ru.averkiev.socialmediaapi.models.FriendshipRequest;
import ru.averkiev.socialmediaapi.models.User;

import java.util.List;

/**
 * Интерфейс предоставляет функционал для взаимодействия пользователей с запросами на дружбу.
 * @author mrGreenNV
 */
public interface FriendshipRequestService {

    /**
     * Позволяет пользователю отправить запрос с предложением дружбы.
     * @param fromUser отправитель запроса.
     * @param toUser получатель запроса.
     * @throws FriendshipRequestCreateException, выбрасывает, если возникла ошибка при создании заявки.
     */
    void sendFriendRequest(User fromUser, User toUser) throws FriendshipRequestCreateException;

    /**
     * Позволяет пользователю принять запрос на дружбу.
     * @param request объект FriendshipRequest, содержащий информацию о запросе.
     */
    void acceptFriendshipRequest(FriendshipRequest request);

    /**
     * Позволяет пользователю отклонить запрос на дружбу.
     * @param request объект FriendshipRequest, содержащий информацию о запросе.
     * @throws FriendshipRequestNotFoundException выбрасывает, если запрос дружбы не найден.
     */
    void declineFriendshipRequest(FriendshipRequest request) throws FriendshipRequestNotFoundException;

    /**
     * Позволяет выполнить поиск дружеской связи между пользователями.
     * @param fromUser пользователь отправивший запрос.
     * @param toUser пользователь, которому запрос предназначен.
     * @return объект FriendshipRequest.
     * @throws FriendshipRequestNotFoundException выбрасывает, если запрос дружбы не найден.
     */
    FriendshipRequest findFriendshipRequest(User fromUser, User toUser) throws FriendshipRequestNotFoundException;

    /**
     * Позволяет удалить запрос на дружбу.
     * @param user пользователь.
     * @param friend друг пользователя.
     * @throws UserFriendNotFoundException выбрасывает, если дружеская связь не найдена.
     * @throws FriendshipRequestNotFoundException выбрасывает, если запрос дружбы не найден.
     * @throws SubscriberNotFoundException выбрасывает, если подписка не найдена.
     * @throws SubscriptionNotFoundException выбрасывает, если подписчик не найден.
     */
    void cancelFriendshipRequest(User user, User friend) throws FriendshipRequestNotFoundException, SubscriberNotFoundException, SubscriptionNotFoundException, UserFriendNotFoundException;

    /**
     * Позволяет получить список отправленных заявок, которые еще не подтверждены.
     * @param user пользователь отправивших заявки.
     * @return список заявок.
     */
    List<FriendshipRequest> getSentPendingFriendshipRequests(User user);

    /**
     * Позволяет получить список полученных заявок, которые еще не подтверждены.
     * @param user пользователь получивший заявки.
     * @return список заявок.
     */
    List<FriendshipRequest> getReceivedPendingFriendshipRequests(User user);

}
