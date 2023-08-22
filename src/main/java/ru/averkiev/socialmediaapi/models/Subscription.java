package ru.averkiev.socialmediaapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс описывает подписку на пользователя.
 * @author mrGreenNV
 */
@Entity
@Table(name = "subscribers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subscription extends BaseEntity {

    /** Пользователь на которого подписались. */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /** Пользователь, являющийся подписчиком. */
    @ManyToOne
    @JoinColumn(name = "target_user_id")
    private User subscriber;

    /** Указывает на наличие дружеских отношений пользователей. */
    @Column(name = "is_friend")
    private boolean isFriend;

}
