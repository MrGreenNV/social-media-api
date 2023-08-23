package ru.averkiev.socialmediaapi.models;

/**
 * Статус запросов дружбы.
 * @author mrGreenNV
 */
public enum FriendshipRequestStatus {

    /** Ожидание подтверждения запроса. */
    PENDING,

    /** Запрос принят. */
    ACCEPT,

    /** Запрос отвергнут. */
    DECLINE
}
