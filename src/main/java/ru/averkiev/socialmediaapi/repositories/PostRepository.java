package ru.averkiev.socialmediaapi.repositories;

import org.springframework.data.domain.PageRequest;
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
     * Выполняет поиск всех постов для пользователя с указанным идентификатором.
     * @param userId идентификатор пользователя.
     * @return список объектов Post.
     */
    List<Post> findAllByUserId(Long userId);

    /**
     * Выполняет поиск всех постов из базы данных сортируя по дате создания.
     * @param pageRequest пагинация запроса.
     * @return список объектов Post.
     */
    List<Post> findLatestPosts(PageRequest pageRequest);

    /**
     * Выполняет поиск постов по идентификаторам их создателей сортируя по дате.
     * @param userIds список идентификаторов создателей постов.
     * @param pageRequest пагинация запроса.
     * @return список объектов Post.
     */
    List<Post> findByUserIdInOrderByCreatedAtDesc(List<Long> userIds, PageRequest pageRequest);
}
