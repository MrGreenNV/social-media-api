package ru.averkiev.socialmediaapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author mrGreenNV
 */
@Entity
@Table(name = "images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Image extends BaseEntity {

    /** Данные изображения. */
    @Lob
    @Column(name = "image_data")
    private byte[] imageData;

    /** Пост, к которому относится данное изображение. */
    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "post_id")
    private Post post;
}
