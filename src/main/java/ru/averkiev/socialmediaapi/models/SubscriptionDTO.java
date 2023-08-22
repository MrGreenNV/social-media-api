package ru.averkiev.socialmediaapi.models;

/**
 * DTO для передачи данных о подписке на пользователя.
 * @author mrGreenNV
 */
public class SubscriptionDTO {

    /** Идентификатор пользователя на которого осуществлена подписка. */
    private Long userId;

    /** Имя пользователя, являющегося подписчиком. */
    private String username;

    /** Указывает на наличие дружеских отношений пользователей. */
    private boolean isFriend;

}
