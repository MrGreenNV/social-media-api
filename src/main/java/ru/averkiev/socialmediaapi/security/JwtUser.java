package ru.averkiev.socialmediaapi.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Класс предназначен для работы с JWT. Он предоставляет информацию о пользователе, необходимую для создания и проверки
 * JWT токенов, а также для аутентификации и авторизации пользователей в системе.
 * @author mrGreenNV
 */
@Data
public class JwtUser implements UserDetails {

    /**
     * Идентификатор пользователя.
     */
    private final Long id;

    /**
     * Имя пользователя в системе.
     */
    private final String username;

    /**
     * Хэшированный пароль.
     */
    private final String password;

    /**
     * Электронная почта пользователя.
     */
    private final String email;

    /**
     * Доступность пользователя.
     */
    private final boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * Получает пароль пользователя.
     * @return пароль.
     */
    @Override
    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    /**
     * Получает имя пользователя в системе.
     * @return имя пользователя.
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * Проверяет актуальность аккаунта.
     * @return в данной версии всегда true.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Проверяет блокировки аккаунта.
     * @return в данной версии всегда true.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Проверяет актуальность Credentials.
     * @return в данной версии всегда true.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Проверяет доступ к аккаунту.
     * @return true, если доступ есть иначе - false.
     */
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
