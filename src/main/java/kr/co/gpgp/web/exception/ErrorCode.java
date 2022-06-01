package kr.co.gpgp.web.exception;

import java.util.Arrays;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "적절하지 않은 요청 값입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 문제가 생겼습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.NOT_FOUND, "적절하지 않은 HTTP 메소드입니다."),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "요청 값의 타입이 잘못되었습니다."),

    // Item
    STOCK_OUT_OF_RANGE(HttpStatus.BAD_REQUEST, "재고의 범위를 벗어났습니다."),
    ;

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public static ErrorCode fromMessage(String message) {
        return Arrays.stream(ErrorCode.values())
            .filter(errorCode -> errorCode.getMessage().equals(message))
            .findFirst()
            .orElse(null);
    }
}
