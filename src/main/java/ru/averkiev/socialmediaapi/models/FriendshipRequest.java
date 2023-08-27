package ru.averkiev.socialmediaapi.models;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Сущность запроса на дружбу")
public class FriendshipRequest extends BaseEntity {

    /** Пользователь от которого поступил запрос на дружбу. */
    @ManyToOne
    @JoinColumn(name = "from_user_id")
    @Schema(description = "Пользователь инициировавший запрос")
    private User fromUser;

    /** Пользователь, которому поступил запрос на дружбу. */
    @ManyToOne
    @JoinColumn(name = "to_user_id")
    @Schema(description = "Пользователь, которому запрос предназначен")
    private User toUser;

    /** Статус запроса. */
    @Enumerated(EnumType.STRING)
    @Column(name = "request_status")
    @Schema(description = "Статус запроса", exampleClasses = FriendshipRequestStatus.class)
    private FriendshipRequestStatus status = FriendshipRequestStatus.PENDING;
}
