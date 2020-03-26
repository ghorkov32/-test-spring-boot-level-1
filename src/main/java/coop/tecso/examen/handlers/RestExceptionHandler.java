package coop.tecso.examen.handlers;

import coop.tecso.examen.exceptions.ApiError;
import coop.tecso.examen.exceptions.ApiErrorFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableWebMvc
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${spring.profiles.active}")
    private String profile;
    //other exception handlers

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ApiError> handleIllegalArgumentException(
            IllegalArgumentException ex) {
        return buildResponseEntity(BAD_REQUEST, ex);
    }

    @ExceptionHandler(IllegalStateException.class)
    protected ResponseEntity<ApiError> handleIllegalStateException(
            IllegalStateException ex) {
        return buildResponseEntity(BAD_REQUEST, ex);
    }

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<ApiError> handleNoSuchElementException(
            NoSuchElementException ex) {
        return buildResponseEntity(NOT_FOUND, ex);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ApiError> handleConstraintViolationException(
            ConstraintViolationException ex) {
        return buildResponseEntity(BAD_REQUEST, ex);
    }

    private ResponseEntity<ApiError> buildResponseEntity(HttpStatus httpStatus, Exception ex) {
        ApiError apiError = ApiErrorFactory.getApiError(httpStatus, ex, profile);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}