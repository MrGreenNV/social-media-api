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
 * Описывает сущность подписчика.
 * @author mrGreenNV
 */
@Entity
@Table(name = "subscribers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subscriber extends BaseEntity {

    /** Пользователь, на которого осуществлена подписка. */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /** Пользователь, являющийся подписчиком. */
    @ManyToOne
    @JoinColumn(name = "follower_user_id")
    private User follower;
}
