package ru.averkiev.socialmediaapi.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.averkiev.socialmediaapi.exceptions.AuthException;
import ru.averkiev.socialmediaapi.exceptions.ImageNotFoundException;
import ru.averkiev.socialmediaapi.exceptions.PostCreationException;
import ru.averkiev.socialmediaapi.exceptions.PostNotFoundException;
import ru.averkiev.socialmediaapi.models.Image;
import ru.averkiev.socialmediaapi.models.Post;
import ru.averkiev.socialmediaapi.repositories.ImageRepository;
import ru.averkiev.socialmediaapi.repositories.PostRepository;
import ru.averkiev.socialmediaapi.services.PostService;

import java.util.List;

/**
 * Класс реализует функционал взаимодействия пользователя с постом.
 * Такой, как создание и обновление поста, а также взаимодействия с изображениями к посту.
 * @author mrGreenNV
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    /** Репозиторий для обращения к базе данных. */
    private final PostRepository postRepository;

    /** Репозиторий для обращения к базе данных. */
    private final ImageRepository imageRepository;

    /** Сервис для взаимодействия с аутентификацией пользователей. */
    private final AuthServiceImpl authService;

    /** Сервис для взаимодействия с пользователями. */
    private final UserServiceImpl userService;

    /**
     * Позволяет создать пост.
     * @param post создаваемый пост.
     * @return созданный пост.
     * @throws PostCreationException выбрасывает при возникновении ошибки при создании поста.
     * @throws AuthException выбрасывает если недостаточно прав.
     */
    @Override
    public Post createPost(Post post) throws PostCreationException, AuthException {

        post.setUser(userService.getUserById(authService.getUserIdFromAuthentication()));

        try {
            post = postRepository.save(post);
            log.info("IN createPost - пост пользователя с идентификатором: {} успешно создан", post.getUser().getId());

            if (!post.getImages().isEmpty()) {
                imageRepository.saveAll(post.getImages());
                log.info("IN createPost - изображения к посту с идентификатором: {} успешно сохранены", post.getId());
            }
            return post;
        } catch (Exception ex) {
            throw new PostCreationException("Ошибка при создании поста.");
        }
    }

    /**
     * Позволяет прикрепить изображения к посту.
     * @param postId идентификатор поста к которому добавляется изображение.
     * @param image прикрепляемое изображение.
     * @throws AuthException выбрасывает если недостаточно прав для редактирования поста.
     * @throws PostNotFoundException выбрасывает если пост с заданным идентификатором не удалось найти в базе данных.
     */
    @Override
    public void addImage(Long postId, Image image) throws AuthException, PostNotFoundException {

        // Поиск поста, в который необходимо добавить изображение.
        Post savePost = postRepository.findById(postId).orElse(null);

        // Проверка существования поста в базе данных.
        if (savePost == null) {
            log.error("IN addImage - ошибка при добавлении изображения. Пост не найден");
            throw new PostNotFoundException("Пост с идентификатором: " + postId + " не найден");
        }

        // Проверка прав для добавления изображения.
        if (!authService.getUserIdFromAuthentication().equals(savePost.getUser().getId())) {
            log.error("IN addImage - ошибка при добавлении изображения. Недостаточно прав");
            throw new AuthException("Недостаточно прав для добавления изображения к посту");
        }

        // Привязка изображения к конкретному посту.
        image.setPost(savePost);

        // Сохранение изображения в базе данных.
        imageRepository.save(image);

        log.info("IN addImage - изображение к посту с идентификатором: {} успешно добавлено", postId);

    }

    /**
     * Позволяет открепить изображение от поста.
     * @param postID идентификатор поста.
     * @param imageId идентификатор изображения.
     * @throws PostNotFoundException выбрасывает, если пост не найден в базе данных.
     * @throws AuthException выбрасывает, если недостаточно прав.
     * @throws ImageNotFoundException выбрасывает, если изображение не найдено в базу данных.
     */
    @Override
    public void deleteImage(Long postID, Long imageId) throws PostNotFoundException, AuthException, ImageNotFoundException {

        // Поиск изображения в базе данных.
        Image saveImage = imageRepository.findById(imageId).orElse(null);

        // Проверка существования изображения в базе данных.
        if (saveImage == null) {
            log.error("IN deleteImage - ошибка при откреплении изображения. Изображения отсутствуют");
            throw new ImageNotFoundException("Изображение с идентификатором: " + imageId + " не найдено");
        }

        // Поиск поста, от которого нужно открепить изображение.
        Post savePost = postRepository.findById(saveImage.getPost().getId()).orElse(null);

        // Проверка существования поста в базе данных.
        if (savePost == null) {
            log.error("IN deleteImage - ошибка при откреплении изображения. Пост не найден");
            throw new PostNotFoundException("Пост с идентификатором: " + saveImage.getPost().getId() + " не найден");
        }

        // Проверка соответствия переданных параметров в запросе.
        if (!postID.equals(saveImage.getPost().getId())) {
            log.error("IN deleteImage - ошибка при откреплении изображения. Несоответствие переданных параметров");
            throw new PostNotFoundException("Параметры идентификатора поста не соответствуют параметрам, переданным в изображении");
        }

        // Проверка прав для открепления изображения.
        if (!authService.getUserIdFromAuthentication().equals(savePost.getUser().getId())) {
            log.error("IN deleteImage - ошибка при откреплении изображения. Недостаточно прав");
            throw new AuthException("Недостаточно прав для добавления изображения к посту");
        }

        // Проверка существования изображений к посту.
        if (savePost.getImages().isEmpty()) {
            log.error("IN deleteImage - ошибка при откреплении изображения. Изображения отсутствуют");
            throw new ImageNotFoundException("К посту с идентификатором: " + savePost.getId() + " не прикреплено ни одного изображения");
        }

        imageRepository.delete(saveImage);
        log.info("IN deleteImage - изображение с идентификатором: {} успешно удалено", imageId);

    }

    /**
     * Позволяет обновить пост.
     * @param postId идентификатор обновляемого поста.
     * @param updatedPost пост с новыми данными.
     * @return объект Post с обновленными данными.
     * @throws PostNotFoundException выбрасывает, если пост не найден в базе данных.
     * @throws AuthException выбрасывает, если недостаточно прав.
     */
    @Override
    public Post updatePost(Long postId, Post updatedPost) throws PostNotFoundException, AuthException {

        // Поиск поста в базе данных, для которого требуется обновление.
        Post savePost = postRepository.findById(postId).orElse(null);

        // Проверка существования поста в базе данных.
        if (savePost == null) {
            log.error("IN updatePost - ошибка при редактировании поста. Пост не найден");
            throw new PostNotFoundException("Пост с идентификатором: " + postId + " не найден");
        }

        // Проверка прав для открепления изображения.
        if (!authService.getUserIdFromAuthentication().equals(savePost.getUser().getId())) {
            log.error("IN updatePost - ошибка при редактировании поста. Недостаточно прав");
            throw new AuthException("Недостаточно прав для редактировании поста");
        }

        if (updatedPost.getTitle() != null) {
            savePost.setTitle(updatedPost.getTitle());
        }

        if (updatedPost.getText() != null) {
            savePost.setText(updatedPost.getText());
        }

        if (!updatedPost.getImages().isEmpty()) {
            savePost.getImages().addAll(updatedPost.getImages());
        }

        // Сохранение обновленного поста в базе данных.
        savePost = postRepository.save(savePost);
        log.info("IN updatePost - пост с идентификатором: {} успешно обновлен", postId);

        return savePost;
    }

    /**
     * Позволяет пользователю удалить свой пост.
     * @param postId идентификатор удаляемого поста.
     * @throws PostNotFoundException выбрасывает, если пост с заданным идентификатором не найден в базе данных.
     * @throws AuthException выбрасывает, если для удаления поста недостаточно прав.
     */
    @Override
    public void deletePost(Long postId) throws PostNotFoundException, AuthException {

        // Поиск поста в базе данных.
        Post savePost = postRepository.findById(postId).orElse(null);

        // Проверка существования поста.
        if (savePost == null) {
            log.error("IN deletePost - ошибка при удалении поста. Пост не найден");
            throw new PostNotFoundException("Пост с идентификатором: " + postId + " не найден");
        }

        // Проверка прав для удаления поста.
        if (!authService.getUserIdFromAuthentication().equals(savePost.getUser().getId())) {
            log.error("IN deletePost - ошибка при редактировании поста. Недостаточно прав");
            throw new AuthException("Недостаточно прав для удаления поста");
        }

        // Удаление объект Post и связанных с ним изображений.
        postRepository.delete(savePost);
        log.info("IN deletePost - пост успешно удален");

    }

    /**
     * Позволяет посмотреть все посты пользователя с пагинацией страниц.
     * @param pageRequest пагинация.
     * @return список постов.
     * @throws AuthException выбрасывает, если произошла ошибка при получении данных из аутентификации пользователя.
     */
    @Override
    public List<Post> showAllPostsByUser(PageRequest pageRequest) throws AuthException {
        Long userId;
        try {
            userId = authService.getUserIdFromAuthentication();
        } catch (Exception ex) {
            log.error("IN showAllPostsByUser - список постов получить не удалось");
            throw new AuthException("Ошибка при получении данных об аутентифицированном пользователе");
        }

        List<Post> posts = postRepository.findAllByUserId(userId, pageRequest);
        log.info("IN showAllPostsByUser - список постов для пользователя с идентификатором: {} успешно получен", userId);
        return posts;
    }

    /**
     * Позволяет посмотреть все посты пользователя.
     * @return список постов.
     * @throws AuthException выбрасывает, если произошла ошибка при получении данных из аутентификации пользователя.
     */
    @Override
    public List<Post> showAllPostsByUser() throws AuthException {
        Long userId;
        try {
            userId = authService.getUserIdFromAuthentication();
        } catch (Exception ex) {
            log.error("IN showAllPostsByUser - список постов получить не удалось");
            throw new AuthException("Ошибка при получении данных об аутентифицированном пользователе");
        }

        List<Post> posts = postRepository.findAllByUserId(userId);
        log.info("IN showAllPostsByUser - список постов для пользователя с идентификатором: {} успешно получен", userId);
        return posts;
    }

    /**
     * Позволяет получить список всех постов отсортированных по дате создания с пагинацией страниц.
     * @param pageRequest пагинация запроса.
     * @return список объектов Post.
     */
    @Override
    public List<Post> getAllPostByCreateAt(PageRequest pageRequest) {
        return postRepository.findAllByOrderByCreatedAtDesc(pageRequest);
    }

    /**
     * Позволяет получить список всех постов отсортированных по дате создания.
     * @return список объектов Post.
     */
    @Override
    public List<Post> getAllPostByCreateAt() {
        return postRepository.findAllByOrderByCreatedAtDesc();
    }

    /**
     * Позволяет получить список постов по идентификаторам их создателей сортируя по дате с пагинацией страниц.
     * @param userIds список идентификаторов создателей постов.
     * @param pageRequest пагинация запроса.
     * @return список объектов Post.
     */
    @Override
    public List<Post> getPostByUserIds(List<Long> userIds, PageRequest pageRequest) {
        return postRepository.findByUserIdInOrderByCreatedAtDesc(userIds, pageRequest);
    }

    /**
     * Позволяет получить список постов по идентификаторам их создателей сортируя по дате.
     * @param userIds список идентификаторов создателей постов.
     * @return список объектов Post.
     */
    @Override
    public List<Post> getPostByUserIds(List<Long> userIds) {
        return postRepository.findByUserIdInOrderByCreatedAtDesc(userIds);
    }
}
