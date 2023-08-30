package ru.averkiev.socialmediaapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.averkiev.socialmediaapi.exceptions.AuthException;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Класс содержит тесты для проверки функциональности {@link JwtProvider}.
 * Тесты охватывают различные сценарии при взаимодействии токенами.
 * Используются моки сервисов для имитации работы с реальными объектами.
 * @author mrGreenNV
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class JwtProviderTest {

    /** Создание ссылки тестируемого объекта. */
    private JwtProvider jwtProvider;

    /** Заглушка для JwtUser. */
    @Mock
    private JwtUser jwtUser;

    /** Инициализация секретного ключа access токена. */
    @Value("${jwt.secret.access}")
    private String jwtAccessSecret;

    /** Инициализация секретного ключа refresh токена. */
    @Value("${jwt.secret.refresh}")
    private String jwtRefreshSecret;

    /** Инициализация срока действия access токена. */
    @Value("${jwt.expiration.access}")
    private Long expirationAccessTokenInMinutes;

    /** Инициализация срока действия refresh токена. */
    @Value("${jwt.expiration.refresh}")
    private Long expirationRefreshTokenInDays;

    /**
     * Выполняется перед тестами.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtProvider = new JwtProvider(
                jwtAccessSecret,
                jwtRefreshSecret,
                expirationAccessTokenInMinutes,
                expirationRefreshTokenInDays
        );
    }

    /**
     * Проверяет генерацию access токена.
     */
    @Test
    public void testGenerateAccessToken() {
        when(jwtUser.getUsername()).thenReturn("testuser");

        String accessToken = jwtProvider.generateAccessToken(jwtUser);

        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret)))
                .build()
                .parseClaimsJws(accessToken)
                .getBody();

        assertNotNull(accessToken);
        assertEquals("testuser", claims.getSubject());
    }

    /**
     * Проверяет генерацию refresh токена.
     */
    @Test
    public void testGenerateRefreshToken() {
        when(jwtUser.getUsername()).thenReturn("testuser");

        String refreshToken = jwtProvider.generateRefreshToken(jwtUser);

        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret)))
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();

        assertNotNull(refreshToken);
        assertEquals("testuser", claims.getSubject());
    }

    /**
     * Проверяет валидацию корректного access токена.
     */
    @Test
    public void testValidateAccessToken_WithValidAccessToken() {
        when(jwtUser.getUsername()).thenReturn("testuser");

        String accessToken = jwtProvider.generateAccessToken(jwtUser);

        boolean result = jwtProvider.validateAccessToken(accessToken);

        assertTrue(result);
    }

    /**
     * Проверяет валидацию корректного refresh токена.
     */
    @Test
    public void testValidateRefreshToken_WithValidRefreshToken() {
        when(jwtUser.getUsername()).thenReturn("testuser");

        String refreshToken = jwtProvider.generateRefreshToken(jwtUser);

        boolean result = jwtProvider.validateRefreshToken(refreshToken);

        assertTrue(result);
    }

    /**
     * Проверяет валидацию просроченного access токена.
     */
    @Test
    public void testValidateAccessToken_WithExpiredAccessToken_ThrowAuthException() {

        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstance = now.minusMinutes(expirationAccessTokenInMinutes).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstance);
        String expiredAccessToken =  Jwts.builder()
                .setSubject(jwtUser.getUsername())
                .setExpiration(accessExpiration)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret)))
                .compact();

        Throwable result = assertThrows(AuthException.class, () -> jwtProvider.validateAccessToken(expiredAccessToken));

        assertEquals(AuthException.class, result.getClass());
        assertEquals("The token expired", result.getMessage());
    }

    /**
     * Проверяет валидацию просроченного refresh токена.
     */
    @Test
    public void testValidateRefreshToken_WithExpiredRefreshToken_ThrowAuthException() {

        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.minusDays(expirationRefreshTokenInDays).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);
        String expiredRefreshToken = Jwts.builder()
                .setSubject(jwtUser.getUsername())
                .setExpiration(refreshExpiration)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret)))
                .compact();

        Throwable result = assertThrows(AuthException.class, () -> jwtProvider.validateRefreshToken(expiredRefreshToken));

        assertEquals(AuthException.class, result.getClass());
        assertEquals("The token expired", result.getMessage());
    }

    /**
     * Проверяет валидацию access токена с невалидной подписью.
     */
    @Test
    public void testValidateAccessToken_AccessTokenWithInvalidSignature_ThrowAuthException() {

        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstance = now.plusMinutes(expirationAccessTokenInMinutes).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstance);
        String accessTokenWithInvalidSignature =  Jwts.builder()
                .setSubject(jwtUser.getUsername())
                .setExpiration(accessExpiration)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret)))
                .compact();

        Throwable result = assertThrows(AuthException.class, () -> jwtProvider.validateAccessToken(accessTokenWithInvalidSignature));

        assertEquals(AuthException.class, result.getClass());
        assertEquals("Invalid signature", result.getMessage());
    }

    /**
     * Проверяет валидацию refresh токена с невалидной подписью.
     */
    @Test
    public void testValidateRefreshToken_RefreshTokenWithInvalidSignature_ThrowAuthException() {

        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstance = now.plusMinutes(expirationAccessTokenInMinutes).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstance);
        String refreshTokenWithInvalidSignature = Jwts.builder()
                .setSubject(jwtUser.getUsername())
                .setExpiration(refreshExpiration)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret)))
                .compact();

        Throwable result = assertThrows(AuthException.class, () -> jwtProvider.validateRefreshToken(refreshTokenWithInvalidSignature));

        assertEquals(AuthException.class, result.getClass());
        assertEquals("Invalid signature", result.getMessage());
    }

    /**
     * Проверяет валидацию неподдерживаемого access токена.
     */
    @Test
    public void testValidateAccessToken_AccessTokenWithUnsupportedJwt_ThrowAuthException() throws NoSuchAlgorithmException {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();

        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstance = now.plusMinutes(expirationAccessTokenInMinutes).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstance);
        String accessTokenWithInvalidSignature =  Jwts.builder()
                .setSubject(jwtUser.getUsername())
                .setExpiration(accessExpiration)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(privateKey)
                .compact();

        Throwable result = assertThrows(AuthException.class, () -> jwtProvider.validateAccessToken(accessTokenWithInvalidSignature));

        assertEquals(AuthException.class, result.getClass());
        assertEquals("Unsupported JWT", result.getMessage());
    }

    /**
     * Проверяет валидацию неподдерживаемого refresh токена.
     */
    @Test
    public void testValidateRefreshToken_RefreshTokenWithUnsupportedJwt_ThrowAuthException() throws NoSuchAlgorithmException {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();

        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstance = now.plusMinutes(expirationAccessTokenInMinutes).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstance);
        String refreshTokenWithInvalidSignature = Jwts.builder()
                .setSubject(jwtUser.getUsername())
                .setExpiration(refreshExpiration)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(privateKey)
                .compact();

        Throwable result = assertThrows(AuthException.class, () -> jwtProvider.validateRefreshToken(refreshTokenWithInvalidSignature));

        assertEquals(AuthException.class, result.getClass());
        assertEquals("Unsupported JWT", result.getMessage());
    }
}