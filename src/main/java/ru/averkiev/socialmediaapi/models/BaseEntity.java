package ru.averkiev.socialmediaapi.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

/**
 * Базовый класс сущностей.
 * @author mrGreenNV
 */
@MappedSuperclass
@Data
public abstract class BaseEntity {

    /** Идентификатор сущности. */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Время создания сущности. */
    @Column(name = "create_at")
    @CreationTimestamp
    private LocalDate createdAt;

    /** Время обновления сущности. */
    @Column(name = "update_at")
    @UpdateTimestamp
    private LocalDate updateAt;

    /** Статус сущности в системе. */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}
