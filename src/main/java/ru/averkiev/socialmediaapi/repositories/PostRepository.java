package ru.averkiev.socialmediaapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.averkiev.socialmediaapi.models.Post;

import java.util.List;

/**
 * Интерфейс представляет собой функциональность взаимодействия объекта Post с базой данных.
 * @author mrGreenNV
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Позволяет получить все посты для пользователя с указанным идентификатором.
     * @param userId идентификатор пользователя.
     * @return список объектов Post.
     */
    List<Post> findAllByUserId(Long userId);
}
