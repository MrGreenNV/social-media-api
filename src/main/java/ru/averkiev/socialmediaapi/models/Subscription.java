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
 * Описывает сущность последователя.
 * @author mrGreenNV
 */
@Entity
@Table(name = "subscriptions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subscription extends BaseEntity {

    /** Пользователь, на которого осуществлена подписка. */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /** Пользователь, являющийся подписчиком. */
    @ManyToOne
    @JoinColumn(name = "target_user_id")
    private User follower;
}
