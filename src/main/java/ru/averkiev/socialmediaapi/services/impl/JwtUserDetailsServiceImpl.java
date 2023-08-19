package ru.averkiev.socialmediaapi.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.averkiev.socialmediaapi.models.User;
import ru.averkiev.socialmediaapi.security.JwtUser;
import ru.averkiev.socialmediaapi.security.JwtUserFactory;

/**
 * Класс предоставляет сервис для загрузки пользователей по имени пользователя, реализуя интерфейс UserDetailsService.
 * Этот класс используется для аутентификации и авторизации пользователей в greenchat с использованием JWT.
 * @author mrGreenNV
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    /**
     * Сервис для взаимодействия с пользователями.
     */
    private final UserServiceImpl userService;

    /**
     * Загружает и возвращает объект UserDetails для пользователя с заданным именем. Использует UserServiceClient для
     * получения информации о пользователе.
     * @param username - имя пользователя, для которого необходимо загрузить и вернуть объект UserDetails.
     * @return - возвращает объект JwtUser с использованием JwtUserFactory.
     * @throws UsernameNotFoundException - выбрасывается в случае, когда по имени не удалось найти пользователя.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user;

        try {
            user = userService.getUserByUsername(username);
        } catch (UsernameNotFoundException unfEx) {
            log.error("IN loadUserByUsername - пользователь с именем: {} не найден", username);
            throw new UsernameNotFoundException("Ошибка при поиске пользователя в БД");
        }

        JwtUser jwtUser = JwtUserFactory.created(user);
        log.info("IN loadUserByUsername - пользователь с именем: {} успешно загружен", username);

        return jwtUser;
    }
}
