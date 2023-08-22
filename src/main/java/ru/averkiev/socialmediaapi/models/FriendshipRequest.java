package ru.averkiev.socialmediaapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Сущность запроса на дружбу.
 * @author mrGreenNV
 */
@Entity
@Table(name = "friendship_requests")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipRequest extends BaseEntity {

    /** Пользователь от которого поступил запрос на дружбу. */
    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    /** Пользователь, которому поступил запрос на дружбу. */
    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private User toUser;

    /** Статус запроса. */
    @Enumerated(EnumType.STRING)
    @Column(name = "request_status")
    private FriendshipRequestStatus status = FriendshipRequestStatus.PENDING;
}
