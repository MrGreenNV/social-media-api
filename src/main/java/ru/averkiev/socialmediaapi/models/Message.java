package ru.averkiev.socialmediaapi.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * Класс описывает сущность сообщений, которые пользователи могут отправлять друг другу.
 * @author mrGreenNV
 */
@Entity
@Table(name = "messages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message extends BaseEntity {

    /** Отправитель сообщения. */
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    /** Получатель сообщения. */
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    /** Текст сообщения. */
    @Column(name = "content")
    private String content;
}
