package ru.averkiev.socialmediaapi.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.averkiev.socialmediaapi.utils.ErrorResponse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс отлавливает все исключения возникающие на уровне контроллера, для предоставления ошибки клиенту в виде JSON.
 * @author mrGreenNV
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Позволяет обработать ошибки связанные с валидацией пользовательских данных.
     * @param ex ошибки при валидации данных.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        List<String> errorMessages = fieldErrors.stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Ошибки при валидации данных",
                request.getRequestURI(),
                errorMessages
        );
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с валидацией регистрацией пользователя.
     * @param urEx ошибка при регистрации пользователя.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<ErrorResponse> handleUserRegistrationException(UserRegistrationException urEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                urEx.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с аутентификацией пользователя.
     * @param authEx ошибка при аутентификации пользователя.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(AuthException authEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.FORBIDDEN,
                authEx.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с лентой активности пользователя.
     * @param afEx ошибка при аутентификации пользователя.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(ActivityFeedException.class)
    public ResponseEntity<ErrorResponse> handleActivityFeedException(ActivityFeedException afEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                afEx.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с лентой активности пользователя.
     * @param unfEx ошибка при аутентификации пользователя.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException unfEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                unfEx.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с созданием запроса на дружбу.
     * @param frcEx ошибка при создании запроса на дружбу.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(FriendshipRequestCreateException.class)
    public ResponseEntity<ErrorResponse> handleFriendshipRequestCreateException(FriendshipRequestCreateException frcEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                frcEx.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с поиском запроса на дружбу.
     * @param fnfEx ошибка при поиске запроса на дружбу.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(FriendshipRequestNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFriendshipRequestNotFoundException(FriendshipRequestNotFoundException fnfEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                fnfEx.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с принятием запроса на дружбу.
     * @param faEx ошибка при принятии запроса на дружбу.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(FriendshipRequestAcceptException.class)
    public ResponseEntity<ErrorResponse> handleFriendshipRequestAcceptException(FriendshipRequestAcceptException faEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                faEx.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с созданием сообщения.
     * @param mcEx ошибка при создании сообщения.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(MessageCreateException.class)
    public ResponseEntity<ErrorResponse> handleMessageCreateException(MessageCreateException mcEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                mcEx.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с поиском сообщения.
     * @param mnfEx ошибка при поиске сообщения.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMessageNotFoundException(MessageNotFoundException mnfEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                mnfEx.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с созданием поста.
     * @param pcEx ошибка при создании поста.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(PostCreationException.class)
    public ResponseEntity<ErrorResponse> handlePostCreationException(PostCreationException pcEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                pcEx.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с поиском поста.
     * @param pnfEx ошибка при поиске поста.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFoundException(PostNotFoundException pnfEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                pnfEx.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с поиском изображения.
     * @param imfEx ошибка при поиске изображения.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleImageNotFoundException(ImageNotFoundException imfEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                imfEx.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с поиском дружеской связи между пользователями.
     * @param unfEx ошибка при поиске дружеской связи.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(UserFriendNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserFriendNotFoundException(UserFriendNotFoundException unfEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                unfEx.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с поиском токена.
     * @param tnfEx ошибка при поиске токена.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTokenNotFoundException(TokenNotFoundException tnfEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                tnfEx.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с поиском подписки.
     * @param snfEx ошибка при поиске подписки.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSubscriptionNotFoundException(SubscriptionNotFoundException snfEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                snfEx.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * Позволяет обработать ошибки связанные с поиском подписчика.
     * @param snfEx ошибка при поиске подписчика.
     * @param request HTTP запрос.
     * @return ResponseEntity, содержащий информацию об ошибке.
     */
    @ExceptionHandler(SubscriberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSubscriberNotFoundException(SubscriberNotFoundException snfEx, HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                snfEx.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
