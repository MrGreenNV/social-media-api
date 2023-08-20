package ru.averkiev.socialmediaapi.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Класс реализует функциональность связанную с аутентификацией.
 * @author mrGreenNV
 */
@Getter
@Setter
public class JwtAuthentication implements Authentication {

    /** Аутентификация в системе. */
    private boolean authenticated;

    /** Имя пользователя в системе. */
    private String username;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }


    /**
     * Получает Principal.
     * @return имя пользователя в системе.
     */
    @Override
    public Object getPrincipal() {
        return this.username;
    }

    /**
     * Проверяет аутентификацию в системе.
     * @return true, если аутентификация есть иначе - false
     */
    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    /**
     * Устанавливает аутентификацию в системе
     * @param isAuthenticated значение аутентификации.
     * @throws IllegalArgumentException выбрасывает в случае ошибки в аргументе.
     */
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return null;
    }
}
