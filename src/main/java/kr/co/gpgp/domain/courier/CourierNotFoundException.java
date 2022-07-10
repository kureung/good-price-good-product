package kr.co.gpgp.domain.courier;

public class CourierNotFoundException extends RuntimeException {

    public CourierNotFoundException() {
        super("배송원이 존재하지 않습니다.");
    }

    public CourierNotFoundException(String message) {
        super(message);
    }

}
