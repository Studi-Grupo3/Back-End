package sptech.school.application.config;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import sptech.school.domain.dto.response.ErrorResponseDTO;
import sptech.school.domain.exception.EmailAlreadyExistsException;
import sptech.school.domain.exception.StorageUnavailableException;
import sptech.school.domain.exception.AuthenticationException;
import sptech.school.domain.exception.UserDontHaveProfilePhoto;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ResponseEntity<ErrorResponseDTO> buildResponse(HttpStatus status, Exception ex) {
        // 1) loga a stacktrace completa no log
        logger.error("Erro capturado no handler global:", ex);

        // 2) extrai a stacktrace para string
        String fullTrace = ExceptionUtils.getStackTrace(ex);

        // 3) monta e retorna o DTO incluindo o campo 'trace'
        ErrorResponseDTO body = new ErrorResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                fullTrace
        );

        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class,
            MissingServletRequestParameterException.class,
            MultipartException.class
    })
    public ResponseEntity<ErrorResponseDTO> handleBadRequest(Exception ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler({
            NoSuchElementException.class,
            EntityNotFoundException.class,
            UserDontHaveProfilePhoto.class
    })
    public ResponseEntity<ErrorResponseDTO> handleNotFound(Exception ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodNotAllowed(Exception ex) {
        return buildResponse(HttpStatus.METHOD_NOT_ALLOWED, ex);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnsupportedMediaType(Exception ex) {
        return buildResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccessDenied(Exception ex) {
        return buildResponse(HttpStatus.FORBIDDEN, ex);
    }

    @ExceptionHandler(StorageUnavailableException.class)
    public ResponseEntity<ErrorResponseDTO> handleStorageUnavailable(Exception ex) {
        return buildResponse(HttpStatus.SERVICE_UNAVAILABLE, ex);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseDTO> handleAuthentication(Exception ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, ex);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmailExists(Exception ex) {
        return buildResponse(HttpStatus.CONFLICT, ex);
    }

    @ExceptionHandler({ IOException.class, Exception.class })
    public ResponseEntity<ErrorResponseDTO> handleGeneric(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }
}
