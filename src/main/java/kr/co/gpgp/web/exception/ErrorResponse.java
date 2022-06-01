package kr.co.gpgp.web.exception;

import static lombok.AccessLevel.PRIVATE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

@Getter
public class ErrorResponse {

    private ErrorCode code;
    private String message;
    private HttpStatus status;
    private List<FieldError> errors;

    private ErrorResponse(ErrorCode code, List<FieldError> errors) {
        this.code = code;
        this.status = code.getStatus();
        this.message = code.getMessage();
        this.errors = errors;
    }

    public static ErrorResponse of(ErrorCode code, List<FieldError> errors) {
        return new ErrorResponse(code, errors);
    }

    public static ErrorResponse of(ErrorCode code) {
        return new ErrorResponse(code, Collections.emptyList());
    }

    public static ErrorResponse of(ErrorCode code, BindingResult bindingResult) {
        return new ErrorResponse(code, FieldError.of(bindingResult));
    }

    @Getter
    @AllArgsConstructor(access = PRIVATE)
    public static class FieldError {

        private String field;
        private String value;
        private String reason;

        public static List<FieldError> of(String field, String value, String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        public static List<FieldError> of(BindingResult bindingResult) {
            List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                .map(error -> new FieldError(
                    error.getField(),
                    error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                    error.getDefaultMessage()))
                .collect(Collectors.toList());
        }
    }

}
