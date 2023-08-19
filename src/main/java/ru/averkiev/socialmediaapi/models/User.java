package ru.averkiev.socialmediaapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import ru.averkiev.socialmediaapi.validations.CustomEmail;
import ru.averkiev.socialmediaapi.validations.CustomUsername;

import java.util.List;

/**
 * Описание пользователя системы.
 * @author mrGreenNV
 */
@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {

    /**
     * Имя пользователя в системе.
     */
    @CustomUsername
    @Column(name = "username")
    private String username;

    /**
     * Хэшированный пароль пользователя.
     */
    @Column(name = "password")
    private String password;

    /**
     * Электронная почта пользователя.
     */
    @CustomEmail
    @Column(name = "email")
    private String email;

    /**
     * Список друзей пользователя.
     */
    @Transient
    private List<User> friends;

    /**
     * Список подписчиков пользователя.
     */
    @Transient
    private List<User> subscribers;

    /**
     * Позволяет создать объект пользователя с заданными параметрами.
     * @param username имя пользователя.
     * @param password хэшированный пароль.
     * @param email электронная почта.
     */
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * Позволяет создать пустой объект пользователя.
     */
    public User() {
    }
}
