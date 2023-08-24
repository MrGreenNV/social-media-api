package ru.averkiev.socialmediaapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс представляет собой пост, написанный пользователем.
 * @author mrGreenNV
 */
@Entity
@Table(name = "posts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность поста пользователей")
public class Post extends BaseEntity {

    /** Заголовок поста */
    @Column(name = "title")
    @Schema(description = "Заголовок поста")
    private String title;

    /** Текст поста */
    @Column(name = "text")
    @Schema(description = "Текстовая часть поста")
    private String text;

    /** Изображения прикреплённые к посту */
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Список изображений, прикрепленных к посту")
    private List<Image> images = new ArrayList<>();

    /** Владелец поста */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
