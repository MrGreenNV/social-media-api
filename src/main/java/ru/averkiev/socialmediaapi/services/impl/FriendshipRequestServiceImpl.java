package ru.averkiev.socialmediaapi.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.averkiev.socialmediaapi.exceptions.FriendshipRequestNotFoundException;
import ru.averkiev.socialmediaapi.exceptions.SubscriberNotFoundException;
import ru.averkiev.socialmediaapi.exceptions.SubscriptionNotFoundException;
import ru.averkiev.socialmediaapi.exceptions.UserFriendNotFoundException;
import ru.averkiev.socialmediaapi.models.*;
import ru.averkiev.socialmediaapi.repositories.FriendshipRequestRepository;
import ru.averkiev.socialmediaapi.services.FriendshipRequestService;

/**
 * Класс реализует функционал для взаимодействия пользователей с запросами на дружбу.
 * @author mrGreenNV
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FriendshipRequestServiceImpl implements FriendshipRequestService {

    /** Репозиторий для обращения к базе данных. */
    private final FriendshipRequestRepository friendshipRequestRepository;

    /** Сервис для взаимодействия с заявками в друзья. */
    private final UserFriendServiceImpl userFriendService;

    /** Сервис для взаимодействия с подписками. */
    private final SubscriberServiceImpl subscriberService;

    /** Сервис для взаимодействия с подписчиками. */
    private final SubscriptionServiceImpl subscriptionService;

    /**
     * Позволяет пользователю отправить запрос с предложением дружбы.
     * @param fromUser отправитель запроса.
     * @param toUser получатель запроса.
     * @return объект FriendshipRequest с информацией о запросе.
     */
    @Override
    public FriendshipRequest sendFriendRequest(User fromUser, User toUser) {
        // Создание нового запроса.
        FriendshipRequest request = new FriendshipRequest();
        request.setFromUser(fromUser);
        request.setToUser(toUser);
        request.setStatus(FriendshipRequestStatus.PENDING);

        // Сохранение запроса в базе данных.
        request = friendshipRequestRepository.save(request);
        log.info("IN sendFriendRequest - запрос на дружбу успешно отправлен");

        // Сохранение подписки.
        subscriberService.save(fromUser, toUser);

        // Сохранение подписчика.
        subscriptionService.save(toUser, fromUser);

        return request;
    }

    /**
     * Позволяет пользователю принять запрос на дружбу.
     * @param request объект FriendshipRequest, содержащий информацию о запросе.
     */
    @Override
    public void acceptFriendshipRequest(FriendshipRequest request) {
        User fromUser = request.getFromUser();
        User toUser = request.getToUser();

        // Создание дружеской связи пользователей.
        userFriendService.save(fromUser, toUser);

        // Обновление статуса заявки.
        request.setStatus(FriendshipRequestStatus.ACCEPT);
        friendshipRequestRepository.save(request);

        // Сохранение подписки.
        subscriptionService.save(toUser, fromUser);

        // Сохранение подписчика.
        subscriberService.save(fromUser, toUser);
    }

    /**
     * Позволяет пользователю отклонить запрос на дружбу.
     * @param request объект FriendshipRequest, содержащий информацию о запросе.
     */
    @Override
    public void declineFriendshipRequest(FriendshipRequest request) {
        // Обновление статуса заявки.
        request.setStatus(FriendshipRequestStatus.DECLINE);
        friendshipRequestRepository.save(request);
    }

    /**
     * Позволяет выполнить поиск дружеской связи между пользователями.
     * @param fromUser пользователь отправивший запрос.
     * @param toUser пользователь, которому запрос предназначен.
     * @return объект FriendshipRequest.
     * @throws FriendshipRequestNotFoundException выбрасывает, если запрос дружбы не найден.
     */
    @Override
    public FriendshipRequest findFriendshipRequest(User fromUser, User toUser) throws FriendshipRequestNotFoundException {
        FriendshipRequest friendshipRequest = friendshipRequestRepository.findFriendshipRequestByFromUserAndToUser(fromUser, toUser).orElse(null);
        if (friendshipRequest == null) {
            throw new  FriendshipRequestNotFoundException("Запрос от пользователя: " + fromUser.getUsername() + " к пользователю " + toUser.getUsername() + " не найден");
        }
        return friendshipRequest;
    }

    /**
     * Позволяет удалить запрос на дружбу.
     * @param user пользователь.
     * @param friend друг пользователя.
     * @throws UserFriendNotFoundException выбрасывает, если дружеская связь не найдена.
     * @throws FriendshipRequestNotFoundException выбрасывает, если запрос дружбы не найден.
     * @throws SubscriberNotFoundException выбрасывает, если подписка не найдена.
     * @throws SubscriptionNotFoundException выбрасывает, если подписчик не найден.
     */
    @Override
    public void cancelFriendshipRequest(User user, User friend) throws FriendshipRequestNotFoundException, UserFriendNotFoundException, SubscriberNotFoundException, SubscriptionNotFoundException {

        // Удаление запроса на дружбу.
        FriendshipRequest friendshipRequest = findFriendshipRequest(user, friend);
        friendshipRequestRepository.delete(friendshipRequest);

        // Удаление дружественной связи.
        userFriendService.deleteByUserAndFriend(user, friend);

        // Удаление подписки.
        subscriberService.deleteByUserAndSubscribedUser(user, friend);

        // Удаление пользователя из подписчиков.
        subscriptionService.deleteByUserAndSubscribedUser(friend, user);

    }
}
