package ru.averkiev.socialmediaapi.security;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Класс представляет собой модель refresh токена.
 * @author mrGreenNV
 */
@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
public class RefreshToken {

    /**
     * Позволяет создать объект RefreshToken с заданными параметрами.
     * @param userId идентификатор пользователя владеющего токеном.
     * @param refreshToken строковое представления токена.
     * @param createdAt дата создания токена.
     * @param expiresAt дата окончания действия токена.
     */
    public RefreshToken(int userId, String refreshToken, LocalDate createdAt, LocalDate expiresAt) {
        this.userId = userId;
        this.refreshToken = refreshToken;
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
    private String refreshToken;

    /** Дата создания токена. */
    @Column(name = "created_at")
    private LocalDate createdAt;

    /** Дата окончания действия токена. */
    @Column(name = "expires_at")
    private LocalDate expiresAt;
}
