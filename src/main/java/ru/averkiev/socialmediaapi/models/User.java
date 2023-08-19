package ru.averkiev.socialmediaapi.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.averkiev.socialmediaapi.validations.CustomEmail;
import ru.averkiev.socialmediaapi.validations.CustomUsername;

@Entity
@Table(name = "users")
@Getter
@Setter
@ApiModel(description = "Сведения о пользователе")

public class User {

    @Id
    @Column(name = "id")
    @ApiModelProperty(notes = "Идентификатор пользователя")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CustomUsername
    @Column(name = "username")
    @ApiModelProperty(notes = "Имя пользователя в системе")
    private String username;


    @Column(name = "password")
    @ApiModelProperty(notes = "Хэшированный пароль пользователя")
    private String password;

    @CustomEmail
    @Column(name = "email")
    @ApiModelProperty(notes = "Электронная почта пользователя")
    private String email;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User() {
    }
}
