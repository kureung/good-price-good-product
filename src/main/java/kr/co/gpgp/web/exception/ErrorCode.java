package kr.co.gpgp.web.exception;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", "적절하지 않은 요청 값입니다."),
    INTERNAL_SERVER_ERROR(500, "C002", "서버에 문제가 생겼습니다."),
    METHOD_NOT_ALLOWED(405, "C003", "적절하지 않은 HTTP 메소드입니다."),
    INVALID_TYPE_VALUE(400, "C005", "요청 값의 타입이 잘못되었습니다."),

    // Item
    STOCK_OUT_OF_RANGE(400, "I001", "재고가 부족합니다."),



    ;

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public static ErrorCode fromMessage(String message) {
        return Arrays.stream(ErrorCode.values())
            .filter(errorCode -> errorCode.getMessage().equals(message))
            .findFirst()
            .orElse(null);
    }
}
