package ru.averkiev.socialmediaapi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Описывает сущность друга пользователя.
 * @author mrGreenNV
 */
@Entity
@Table(name = "user_friend")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFriend extends BaseEntity {

    /** Пользователь имеющий друга. */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /** Пользователь являющийся другом. */
    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User friend;


}
