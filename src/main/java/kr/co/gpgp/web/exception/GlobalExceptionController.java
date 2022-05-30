package kr.co.gpgp.web.exception;

import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler
    public ResponseEntity<String> illegalArgumentExHandler(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.internalServerError()
            .body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> noSuchElementExHandler(NoSuchElementException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest()
            .body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> exHandler(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.internalServerError()
            .body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> typeMistMatchExHandler(TypeMismatchException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.internalServerError()
            .body(e.getMessage());
    }

}
