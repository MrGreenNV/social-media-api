package ru.averkiev.socialmediaapi.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO поста для удобного отображения пользователю.
 * @author mrGreenNV
 */
@Data
public class PostDTO {

    /** Заголовок поста. */
    private String title;

    /** Текстовая часть поста. */
    private String text;

    /** Список изображений к посту. */
    private List<Image> images = new ArrayList<>();

    /** Имя создателя поста. */
    private String username;
}
