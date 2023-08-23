package ru.averkiev.socialmediaapi.services;

import ru.averkiev.socialmediaapi.exceptions.FriendshipRequestNotFoundException;
import ru.averkiev.socialmediaapi.exceptions.SubscriberNotFoundException;
import ru.averkiev.socialmediaapi.exceptions.SubscriptionNotFoundException;
import ru.averkiev.socialmediaapi.exceptions.UserFriendNotFoundException;
import ru.averkiev.socialmediaapi.models.FriendshipRequest;
import ru.averkiev.socialmediaapi.models.User;

/**
 * Интерфейс предоставляет функционал для взаимодействия пользователей с запросами на дружбу.
 * @author mrGreenNV
 */
public interface FriendshipRequestService {

    /**
     * Позволяет пользователю отправить запрос с предложением дружбы.
     * @param fromUser отправитель запроса.
     * @param toUser получатель запроса.
     * @return объект FriendshipRequest с информацией о запросе.
     */
    FriendshipRequest sendFriendRequest(User fromUser, User toUser);

    /**
     * Позволяет пользователю принять запрос на дружбу.
     * @param request объект FriendshipRequest, содержащий информацию о запросе.
     */
    void acceptFriendshipRequest(FriendshipRequest request);

    /**
     * Позволяет пользователю отклонить запрос на дружбу.
     * @param request объект FriendshipRequest, содержащий информацию о запросе.
     * @throws UserFriendNotFoundException выбрасывает, если дружеская связь не найдена.
     */
    void declineFriendshipRequest(FriendshipRequest request);

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
    void cancelFriendshipRequest(User user, User friend) throws FriendshipRequestNotFoundException, SubscriberNotFoundException, SubscriptionNotFoundException;
}
