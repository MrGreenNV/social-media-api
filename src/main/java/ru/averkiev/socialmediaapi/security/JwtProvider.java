package ru.averkiev.socialmediaapi.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author mrGreenNV
 */
@Slf4j
@Component
public class JwtProvider {

    /**
     * Секретный ключ для подписи токенов доступа.
     */
    private final SecretKey jwtAccessSecret;

    /**
     * Секретный ключ для подписи токенов обновления.
     */
    private final SecretKey jwtRefreshSecret;

    /**
     * Срок действия access токена.
     */
    private final long expirationAccessTokenInMinutes;

    /**
     * Срок действия refresh токена.
     */
    private final long expirationRefreshTokenInDays;

    /**
     * Позволяет создать объект JwtProvider с заданными параметрами
     * @param jwtAccessSecret секретный ключ для подписи токенов доступа.
     * @param jwtRefreshSecret секретный ключ для подписи токенов обновления.
     * @param expirationAccessTokenInMinutes срок действия access токена.
     * @param expirationRefreshTokenInDays срок действия refresh токена.
     */
    @Autowired
    public JwtProvider(@Value("${jwt.secret.access}") String jwtAccessSecret,
                       @Value("${jwt.secret.refresh}") String jwtRefreshSecret,
                       @Value("${jwt.expiration.access}") Long expirationAccessTokenInMinutes,
                       @Value("${jwt.expiration.refresh}") Long expirationRefreshTokenInDays
    ) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
        this.expirationAccessTokenInMinutes = expirationAccessTokenInMinutes;
        this.expirationRefreshTokenInDays = expirationRefreshTokenInDays;
    }

    /**
     * Генерирует и возвращает токен доступа на основе переданного объекта JwtUser. Метод создаёт токен с
     * указанным субъектом (именем пользователя), сроком действия и подписывает его с использованием
     * секретного ключа jwtAccessSecret.
     * @param jwtUser передаваемый объект, для которого генерируется токен доступа.
     * @return строка, содержащая токен доступа.
     */
    public String generateAccessToken(@NotNull JwtUser jwtUser) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstance = now.plusMinutes(expirationAccessTokenInMinutes).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstance);
        return Jwts.builder()
                .setSubject(jwtUser.getUsername())
                .setExpiration(accessExpiration)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(jwtAccessSecret)
                .compact();
    }

    /**
     * Генерирует и возвращает токен обновления на основе переданного объекта JwtUser. Метод создаёт токен с
     * указанным субъектом (именем пользователя), сроком действия и подписывает его с использованием
     * секретного ключа jwtAccessSecret.
     * @param jwtUser передаваемый объект, для которого генерируется токен обновления.
     * @return строка, содержащая токен обновления.
     */
    public String generateRefreshToken(@NotNull JwtUser jwtUser) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusDays(expirationRefreshTokenInDays).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .setSubject(jwtUser.getUsername())
                .setExpiration(refreshExpiration)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(jwtRefreshSecret)
                .compact();
    }

    /**
     * Парсит токен.
     * @param token переданный токен.
     * @param secret секретный ключ для разбора токена и проверки его целостности.
     */
    public void parseToken(@NotNull String token, @NotNull Key secret) throws Exception {
        Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token);
    }

    /**
     * Парсит токен доступа.
     * @param accessToken передаваемый токен доступа.
     */
    public void parseAccessToken(@NotNull String accessToken) throws Exception {
        parseToken(accessToken, jwtAccessSecret);
    }

    /**
     * Парсит токен обновления.
     * @param refreshToken передаваемый токен доступа, который необходимо проверить.
     */
    public void parseRefreshToken(@NotNull String refreshToken) throws Exception {
        parseToken(refreshToken, jwtRefreshSecret);
    }

    /**
     * Извлекает и возвращает объект Claims из разобранного токена. Метод использует парсер для разбора токена и
     * извлечения полезной нагрузки (payload) токена.
     * @param token передаваемый токен, из которого необходимо извлечь объект Claims.
     * @param secret секретный ключ для разбора передаваемого токена.
     * @return объект Claims, содержащий body переданного токена.
     */
    private Claims getClaims(@NotNull String token, @NotNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Извлекает и возвращает объект Claims из разобранного access токена, с помощью вызова метода getClaims.
     * @param AccessToken - токен, из которого извлекаются Claims.
     * @return объект Claims, содержащий body переданного токена.
     */
    public Claims getAccessClaims(@NotNull String AccessToken) {
        return getClaims(AccessToken, jwtAccessSecret);
    }

    /**
     * Извлекает и возвращает объект Claims из разобранного refresh токена, с помощью вызова метода getClaims.
     * @param refreshToken - токен, из которого извлекаются Claims.
     * @return объект Claims, содержащий body переданного токена.
     */
    public Claims getRefreshClaims(@NotNull String refreshToken) {
        return getClaims(refreshToken, jwtRefreshSecret);
    }

}
