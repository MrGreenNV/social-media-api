package ru.averkiev.socialmediaapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    /** Имя пользователя в системе. */
    @Column(name = "username")
    private String username;

    /** Хэшированный пароль пользователя. */
    @Column(name = "password")
    private String password;

    /** Электронная почта пользователя. */
    @Column(name = "email")
    private String email;

    /** Список отправленных запросов дружбы. */
    @JsonIgnore
    @OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendshipRequest> sentFriendshipRequests;

    /** Список полученных запросов дружбы. */
    @JsonIgnore
    @OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendshipRequest> receivedFriendshipRequests;

    /** Список постов пользователя. */
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    /** Список подписок пользователя */
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subscription> subscriptions;

    /** Список подписчиков пользователя */
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subscriber> subscribers;

    /** Список друзей пользователя. */
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFriend> friends;

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
