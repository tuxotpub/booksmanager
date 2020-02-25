package org.tuxotpub.booksmanager.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.InvalidMimeTypeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

/**
 * Created by tuxsamo.
 */
@Slf4j
@RestControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    public RestResponseExceptionHandler() {
        super();
    }

    @Override
    protected final ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException e, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.error(status + ": {}", e.getMessage());
        return handleExceptionInternal(e, getMessage(e, request), headers, status, request);

    }

    @Override
    protected final ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.error(status + ": {}", e.getMessage());
        return handleExceptionInternal(e, getMessage(e, request), headers, status, request);
    }


    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAnyException(final Exception e, final WebRequest request) {
        log.error(BAD_REQUEST.getReasonPhrase() + ": " + e.getMessage(), e);
        return handleExceptionInternal(e, getMessage(e, request), new HttpHeaders(), BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class, NumberFormatException.class, ConstraintViolationException.class, DataIntegrityViolationException.class})
    public final ResponseEntity<Object> handleBadRequest(final RuntimeException e, final WebRequest request) {
        log.error(BAD_REQUEST.getReasonPhrase() + ": {}", e);
        return handleExceptionInternal(e, getMessage(e, request), new HttpHeaders(), BAD_REQUEST, request);
    }

    @ExceptionHandler({InvalidDataAccessApiUsageException.class, DataAccessException.class,
            OptimisticLockingFailureException.class})
    protected ResponseEntity<Object> handleConflict(final RuntimeException e, final WebRequest request) {
        log.error(CONFLICT + ": {}", e);
        return handleExceptionInternal(e, getMessage(e, request), new HttpHeaders(), BAD_REQUEST, request);
    }

    @ExceptionHandler({InvalidMimeTypeException.class, InvalidMediaTypeException.class})
    protected ResponseEntity<Object> handleInvalidMimeTypeException(final IllegalArgumentException e, final WebRequest request) {
        log.error(UNSUPPORTED_MEDIA_TYPE + ": {}", e.getMessage());
        return handleExceptionInternal(e, getMessage(e, request), new HttpHeaders(), UNSUPPORTED_MEDIA_TYPE, request);
    }

    @ExceptionHandler({InvocationTargetException.class, NullPointerException.class, IllegalArgumentException.class,
            IllegalStateException.class, ConversionFailedException.class, ClassCastException.class})
    public ResponseEntity<Object> handle500s(final RuntimeException e, final WebRequest request) {
        log.error(BAD_REQUEST.getReasonPhrase(), e);
        return handleExceptionInternal(e, getMessage(e, request), new HttpHeaders(), BAD_REQUEST, request);
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<Object> handleAccessDeniedException(
            Exception e, WebRequest request) {
        log.error(FORBIDDEN + ": {}", e);
        return new ResponseEntity<Object>("Access denied!", new HttpHeaders(), FORBIDDEN);

    }

    private ErrorMessage getMessage(final Exception e, WebRequest request) {
        return new ErrorMessage(LocalDateTime.now(), e.getMessage(), request.getDescription(false));
    }
}
