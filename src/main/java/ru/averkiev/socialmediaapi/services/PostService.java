package ru.averkiev.socialmediaapi.services;

import org.springframework.data.domain.PageRequest;
import ru.averkiev.socialmediaapi.exceptions.AuthException;
import ru.averkiev.socialmediaapi.exceptions.ImageNotFoundException;
import ru.averkiev.socialmediaapi.exceptions.PostCreationException;
import ru.averkiev.socialmediaapi.exceptions.PostNotFoundException;
import ru.averkiev.socialmediaapi.models.Image;
import ru.averkiev.socialmediaapi.models.Post;

import java.util.List;

/**
 * Интерфейс определяет функциональность для постов пользователей, таких как создание, обновление.
 * @author mrGreenNV
 */
public interface PostService {

    /**
     * Позволяет создать пост.
     * @param post создаваемый пост.
     * @return созданный пост.
     * @throws PostCreationException выбрасывает при возникновении ошибки при создании поста.
     */
    Post createPost(Post post) throws PostCreationException;

    /**
     * Позволяет прикрепить изображения к посту.
     * @param postId идентификатор поста к которому добавляется изображение.
     * @param image прикрепляемое изображение.
     * @throws AuthException выбрасывает если недостаточно прав для редактирования поста.
     * @throws PostNotFoundException выбрасывает если пост с заданным идентификатором не удалось найти в базе данных.
     */
    void addImage(Long postId, Image image) throws AuthException, PostNotFoundException;

    /**
     * Позволяет открепить изображение от поста.
     * @param postId идентификатор поста.
     * @param imageId идентификатор изображения.
     * @throws PostNotFoundException выбрасывает, если пост не найден в базе данных.
     * @throws AuthException выбрасывает, если недостаточно прав.
     * @throws ImageNotFoundException выбрасывает, если изображение не найдено в базу данных.
     */
    void deleteImage(Long postId, Long imageId) throws PostNotFoundException, AuthException, ImageNotFoundException;

    /**
     * Позволяет обновить пост.
     * @param postId идентификатор обновляемого поста.
     * @param updatedPost пост с новыми данными.
     * @return обновленный пост.
     */
    Post updatePost(Long postId, Post updatedPost) throws PostNotFoundException, AuthException;

    /**
     * Позволяет пользователю удалить свой пост.
     * @param postId идентификатор удаляемого поста.
     * @throws PostNotFoundException выбрасывает, если пост с заданным идентификатором не найден в базе данных.
     * @throws AuthException выбрасывает, если для удаления поста недостаточно прав.
     */
    void deletePost(Long postId) throws PostNotFoundException, AuthException;

    /**
     * Позволяет посмотреть все посты пользователя с пагинацией страниц.
     * @param pageRequest пагинация запроса.
     * @return список постов.
     * @throws AuthException выбрасывает, если произошла ошибка при получении данных из аутентификации пользователя.
     */
    List<Post> showAllPostsByUser(PageRequest pageRequest);

    /**
     * Позволяет посмотреть все посты пользователя.
     * @return список постов.
     * @throws AuthException выбрасывает, если произошла ошибка при получении данных из аутентификации пользователя.
     */
    List<Post> showAllPostsByUser();

    /**
     * Позволяет получить список всех постов отсортированных по дате создания с пагинацией страниц.
     * @param pageRequest пагинация запроса.
     * @return список объектов Post.
     */
    List<Post> getAllPostByCreateAt(PageRequest pageRequest);

    /**
     * Позволяет получить список всех постов отсортированных по дате создания.
     * @return список объектов Post.
     */
    List<Post> getAllPostByCreateAt();

    /**
     * Позволяет получить список постов по идентификаторам их создателей сортируя по дате.
     * @param userIds список идентификаторов создателей постов.
     * @param pageRequest пагинация запроса.
     * @return список объектов Post.
     */
    List<Post> getPostByUserIds(List<Long> userIds, PageRequest pageRequest);
}
