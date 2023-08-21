package ru.averkiev.socialmediaapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.socialmediaapi.models.Image;

/**
 * Интерфейс представляет собой функциональность взаимодействия объекта Image с базой данных.
 * @author mrGreenNV
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
