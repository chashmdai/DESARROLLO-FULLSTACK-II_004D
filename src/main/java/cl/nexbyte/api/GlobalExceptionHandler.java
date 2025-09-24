package cl.nexbyte.api;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.OffsetDateTime;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
    List<Map<String,String>> fields = ex.getBindingResult().getFieldErrors().stream()
      .map(e -> Map.of("field", e.getField(), "message", e.getDefaultMessage()))
      .toList();
    return build(req, HttpStatus.UNPROCESSABLE_ENTITY, "Validation failed", "Errores de validación", fields);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ApiError> handleConstraint(ConstraintViolationException ex, HttpServletRequest req) {
    List<Map<String,String>> violations = ex.getConstraintViolations().stream()
      .map(this::toMap).toList();
    return build(req, HttpStatus.UNPROCESSABLE_ENTITY, "Constraint violation", "Parámetros inválidos", violations);
  }

  @ExceptionHandler({
    MissingServletRequestParameterException.class,
    HttpMessageNotReadableException.class,
    MethodArgumentTypeMismatchException.class
  })
  public ResponseEntity<ApiError> handleBadRequest(Exception ex, HttpServletRequest req) {
    return build(req, HttpStatus.BAD_REQUEST, "Bad request", ex.getMessage(), null);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ApiError> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex, HttpServletRequest req) {
    return build(req, HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed", ex.getMessage(), null);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ApiError> handleNotFound(EntityNotFoundException ex, HttpServletRequest req) {
    return build(req, HttpStatus.NOT_FOUND, "Not found", ex.getMessage(), null);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ApiError> handleConflict(DataIntegrityViolationException ex, HttpServletRequest req) {
    return build(req, HttpStatus.CONFLICT, "Data integrity violation", rootMessage(ex), null);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiError> handleIllegalArg(IllegalArgumentException ex, HttpServletRequest req) {
    return build(req, HttpStatus.UNPROCESSABLE_ENTITY, "Illegal argument", ex.getMessage(), null);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest req) {
    return build(req, HttpStatus.INTERNAL_SERVER_ERROR, "Internal error", rootMessage(ex), null);
  }

  private ResponseEntity<ApiError> build(HttpServletRequest req, HttpStatus status, String error, String message, Object details) {
    ApiError body = new ApiError(
      OffsetDateTime.now(),
      req.getRequestURI(),
      status.value(),
      error,
      message,
      details
    );
    return new ResponseEntity<>(body, new HttpHeaders(), status);
  }

  private Map<String,String> toMap(ConstraintViolation<?> v) {
    String field = Optional.ofNullable(v.getPropertyPath()).map(Object::toString).orElse("param");
    String msg = Optional.ofNullable(v.getMessage()).orElse("invalid");
    return Map.of("field", field, "message", msg);
  }

  private String rootMessage(Throwable t) {
    Throwable r = t;
    while (r.getCause() != null) r = r.getCause();
    return r.getMessage() != null ? r.getMessage() : t.toString();
  }
}
