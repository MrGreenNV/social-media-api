package ru.averkiev.socialmediaapi.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.averkiev.socialmediaapi.exceptions.RegistrationException;
import ru.averkiev.socialmediaapi.exceptions.UserNotFoundException;
import ru.averkiev.socialmediaapi.models.User;
import ru.averkiev.socialmediaapi.models.UserCreateDTO;
import ru.averkiev.socialmediaapi.repositories.UserRepository;
import ru.averkiev.socialmediaapi.services.UserService;

import java.util.List;

/**
 * Класс реализует функционал взаимодействия User с базой данных.
 * @author mrGreenNV
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    /** ModelMapper для преобразования объектов DTO и объектов модели. */
    private final ModelMapper modelMapper;

    /** Репозиторий для обращения к базе данных. */
    private final UserRepository userRepository;

    /** Сервис для взаимодействия с хэшированными паролями. */
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Регистрирует нового пользователя в системе.
     * @param userCreateDTO DTO данные нового пользователя.
     * @return зарегистрированный пользователь.
     * @throws RegistrationException выбрасывает если регистрация пользователя не удалась по каким-либо причинам.
     */
    @Override
    public UserCreateDTO register(UserCreateDTO userCreateDTO) throws RegistrationException {

        User user = modelMapper.map(userCreateDTO, User.class);

        // Проверка на дублирование имени пользователя.
        if (existUserByUsername(user.getUsername())) {
            log.error("IN register - пользователь с именем: '{}' не был зарегистрирован", userCreateDTO.getUsername());
            throw new RegistrationException("Пользователь с таким именем уже зарегистрирован");
        }

        // Проверка на дублирование email.
        if (existsUserByEmail(user.getEmail())) {
            log.error("IN register - пользователь с именем: '{}' не был зарегистрирован", userCreateDTO.getUsername());
            throw new RegistrationException("Пользователь с таким email уже зарегистрирован");
        }

       // Хэширование пароля для безопасности хранения в базе данных.
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        // Вызов метода для сохранения пользователя в БД.
        user = saveUser(user);

        return modelMapper.map(user, UserCreateDTO.class);
    }

    /**
     * Проверяет, существует ли пользователь с указанной электронной почтой.
     * @param email электронная почта проверяемого пользователя.
     * @return true, если пользователь существует, иначе false.
     */
    private boolean existsUserByEmail(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    /**
     * Проверяет, существует ли пользователь с указанным именем.
     * @param username имя проверяемого пользователя.
     * @return true, если пользователь существует, иначе false.
     */
    private boolean existUserByUsername(String username) {
        return userRepository.findUserByUsername(username).isPresent();
    }

    /**
     * Создаёт нового пользователя в системе
     * @param user новый пользователь.
     * @return созданный пользователь.
     */
    @Override
    public User saveUser(User user) {
        user = userRepository.save(user);
        log.info("IN saveUser - пользователь с логином '{}' успешно сохранен", user.getUsername());
        return user;
    }

    /**
     * Возвращает пользователя по его идентификатору.
     * @param userId идентификатор искомого пользователя.
     * @return пользователя.
     * @throws UserNotFoundException выбрасывает если пользователь с заданным именем не был найден.
     */
    @Override
    public User getUserById(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            log.error("IN getUserById - пользователь с идентификатором: {} не найден", userId);
            throw new UserNotFoundException("Пользователь с идентификатором: " + userId + " не найден");
        }

        log.info("IN getUserById - пользователь с идентификатором: {} успешно найден", userId);

        return user;
    }

    /**
     * Возвращает пользователя по его имени в системе.
     * @param username имя пользователя.
     * @return пользователя.
     * @throws UserNotFoundException выбрасывает если пользователь с заданным именем не был найден.
     */
    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findUserByUsername(username).orElse(null);

        if (user == null) {
            log.error("IN getUserByUsername - пользователь с именем: {} не найден", username);
            throw new UserNotFoundException("Пользователь с именем: " + username + " не найден");
        }

        log.info("IN getUserByUsername - пользователь с именем: {} успешно найден", username);

        return user;
    }

    /**
     * Возвращает список всех пользователей.
     * @return список пользователей.
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
