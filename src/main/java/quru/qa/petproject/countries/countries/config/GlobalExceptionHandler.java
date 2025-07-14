package quru.qa.petproject.countries.countries.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleValidationExceptions(
        MethodArgumentNotValidException ex,
        HttpServletRequest request
    ) {
        List<Map<String, Object>> errorList = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> {
                Map<String, Object> err = new HashMap<>();
                err.put("field", error.getField());
                err.put("message", error.getDefaultMessage());
                err.put("rejectedValue", error.getRejectedValue());
                return err;
            })
            .toList();

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        responseBody.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        responseBody.put("message", "Validation failed");
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("path", request.getRequestURI());
        responseBody.put("errors", errorList);

        return ResponseEntity.badRequest().body(responseBody);
    }
}
