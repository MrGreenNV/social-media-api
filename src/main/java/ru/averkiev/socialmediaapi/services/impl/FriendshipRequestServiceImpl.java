package ru.averkiev.socialmediaapi.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.averkiev.socialmediaapi.exceptions.*;
import ru.averkiev.socialmediaapi.models.*;
import ru.averkiev.socialmediaapi.repositories.FriendshipRequestRepository;
import ru.averkiev.socialmediaapi.services.FriendshipRequestService;

import java.util.List;

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
     * @throws FriendshipRequestCreateException выбрасывает, если возникла ошибка при создании заявки.
     */
    @Override
    public void sendFriendRequest(User fromUser, User toUser) throws FriendshipRequestCreateException {
        // Проверка на существование такой заявки.
        try {
            if (findFriendshipRequest(fromUser, toUser) != null) {
                log.error("IN sendFriendRequest - запрос на дружбу не создан");
                throw new FriendshipRequestCreateException("Запрос на дружбу от пользователя: " + fromUser.getUsername() + " к пользователю " + toUser.getUsername() + " уже существует");
            }
        } catch (FriendshipRequestNotFoundException fnfEx) {
            log.info("IN sendFriendRequest - дубликат запроса отсутствует");
        }

        // Создание нового запроса.
        FriendshipRequest request = new FriendshipRequest();
        request.setFromUser(fromUser);
        request.setToUser(toUser);
        request.setStatus(FriendshipRequestStatus.PENDING);

        // Сохранение запроса в базе данных.
        friendshipRequestRepository.save(request);
        log.info("IN sendFriendRequest - запрос на дружбу успешно создан");

        // Сохранение подписки.
        subscriptionService.save(fromUser, toUser);

        // Сохранение подписчика.
        subscriberService.save(toUser, fromUser);
    }

    /**
     * Позволяет пользователю принять запрос на дружбу.
     * @param request объект FriendshipRequest, содержащий информацию о запросе.
     */
    @Override
    public void acceptFriendshipRequest(FriendshipRequest request) {

        if (request.getStatus() != FriendshipRequestStatus.PENDING) {
            log.error("IN acceptFriendshipRequest - статус заявки не соответствует запросу");
            throw new FriendshipRequestAcceptException("Статус заявки на дружбу не соответствует запросу на принятие заявки. Текущий статус: " + request.getStatus() + ". Ожидаемый статус: " +  FriendshipRequestStatus.PENDING);
        }

        User fromUser = request.getFromUser();
        User toUser = request.getToUser();

        // Создание дружеской связи пользователей.
        userFriendService.save(fromUser, toUser);
        userFriendService.save(toUser, fromUser);

        // Обновление статуса заявки.
        request.setStatus(FriendshipRequestStatus.ACCEPT);
        friendshipRequestRepository.save(request);
        log.info("IN acceptFriendshipRequest - запрос на дружбу успешно принят");

        // Сохранение подписки.
        subscriptionService.save(toUser, fromUser);

        // Сохранение подписчика.
        subscriberService.save(fromUser, toUser);
    }

    /**
     * Позволяет пользователю отклонить запрос на дружбу.
     * @param request объект FriendshipRequest, содержащий информацию о запросе.
     * @throws FriendshipRequestNotFoundException выбрасывает, если запрос дружбы не найден.
     */
    @Override
    public void declineFriendshipRequest(FriendshipRequest request) throws FriendshipRequestNotFoundException {
        // Обновление статуса заявки.
        request.setStatus(FriendshipRequestStatus.DECLINE);
        friendshipRequestRepository.save(request);
        log.info("IN declineFriendshipRequest - запрос на дружбу успешно отклонен");

        // Удаление запроса на дружбу.
        friendshipRequestRepository.delete(request);
        log.info("IN declineFriendshipRequest - запрос на дружбу успешно удален");
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
    public void cancelFriendshipRequest(User user, User friend)
            throws FriendshipRequestNotFoundException, UserFriendNotFoundException, SubscriberNotFoundException, SubscriptionNotFoundException {

        // Удаление дружественной связи.
        userFriendService.deleteByUserAndFriend(user, friend);
        userFriendService.deleteByUserAndFriend(friend, user);

        // Удаление подписки.
        subscriptionService.deleteByUserAndSubscribedUser(friend, user);

        // Удаление пользователя из подписчиков.
        subscriberService.deleteByUserAndSubscribedUser(user, friend);

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
            log.error("IN findFriendshipRequest - запрос на дружбу не найден");
            throw new  FriendshipRequestNotFoundException("Запрос от пользователя: " + fromUser.getUsername() + " к пользователю " + toUser.getUsername() + " не найден");
        }
        log.info("IN findFriendshipRequest - запрос на дружбу успешно найден");
        return friendshipRequest;
    }

    /**
     * Позволяет получить список отправленных заявок, которые еще не подтверждены.
     * @param user пользователь отправивших заявки.
     * @return список заявок.
     */
    @Override
    public List<FriendshipRequest> getSentPendingFriendshipRequests(User user) {
        return friendshipRequestRepository.findAllByFromUserAndStatus(user, FriendshipRequestStatus.PENDING);
    }

    /**
     * Позволяет получить список полученных заявок, которые еще не подтверждены.
     * @param user пользователь получивший заявки.
     * @return список заявок.
     */
    @Override
    public List<FriendshipRequest> getReceivedPendingFriendshipRequests(User user) {
        return friendshipRequestRepository.findAllByToUserAndStatus(user, FriendshipRequestStatus.PENDING);
    }
}
