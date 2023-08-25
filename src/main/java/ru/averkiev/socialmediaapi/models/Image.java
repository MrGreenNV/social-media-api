package ru.averkiev.socialmediaapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Сущность изображения для поста
 * @author mrGreenNV
 */
@Entity
@Table(name = "images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность изображения для поста")
public class Image extends BaseEntity {

    /** Данные изображения. */
    @Lob
    @Column(name = "image_data")
    @Schema(description = "Данные изображения, представленные в массиве байт")
    private byte[] imageData;

    /** Пост, к которому относится данное изображение. */
    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "post_id")
    private Post post;
}
