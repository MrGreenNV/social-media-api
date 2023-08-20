package ru.averkiev.socialmediaapi.security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Класс представляет собой модель access токена.
 * @author mrGreenNV
 */
@Entity
@Table(name = "access_tokens")
@Getter
@Setter
@NoArgsConstructor
public class AccessToken {

    /**
     * Позволяет создать объект AccessToken с заданными параметрами.
     * @param userId идентификатор пользователя владеющего токеном.
     * @param accessToken строковое представления токена.
     * @param createdAt дата создания токена.
     * @param expiresAt дата окончания действия токена.
     */
    public AccessToken(int userId, String accessToken, LocalDate createdAt, LocalDate expiresAt) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    /** Идентификатор токена. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /** Идентификатор пользователя владеющего токеном. */
    @Column(name = "user_id")
    private int userId;

    /** Строковое представления токена. */
    @Column(name = "token")
    private String accessToken;

    /** Дата создания токена. */
    @Column(name = "created_at")
    private LocalDate createdAt;

    /** Дата окончания действия токена. */
    @Column(name = "expires_at")
    private LocalDate expiresAt;
}