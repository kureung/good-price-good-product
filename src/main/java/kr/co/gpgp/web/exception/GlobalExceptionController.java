package kr.co.gpgp.web.exception;

import java.util.List;
import kr.co.gpgp.web.exception.ErrorResponse.FieldError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exHandler(Exception e) {

        log.error(e.getMessage(), e);

        ErrorCode errorCode = ErrorCode.fromMessage(e.getMessage());

        if (errorCode==null) {
            errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        }

        final ErrorResponse response = ErrorResponse.of(errorCode);
        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> bindExHandler(BindException e) {

        log.error(e.getMessage(), e);

        final ErrorResponse response = ErrorResponse.of(
                ErrorCode.INVALID_INPUT_VALUE,
                e.getBindingResult());

        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> methodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e) {

        log.error(e.getMessage(), e);

        final String value = e.getValue()==null ? "":e.getValue().toString();
        List<FieldError> errors = FieldError.of(e.getName(), value, e.getErrorCode());
        ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_TYPE_VALUE, errors);

        return ResponseEntity.status(response.getStatus())
                .body(response);
    }

}
