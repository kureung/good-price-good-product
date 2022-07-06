package kr.co.gpgp.domain.address;

public class AddressNotFoundException extends RuntimeException {

    public AddressNotFoundException() {
        super("주소를 찾을수 없습니다.");
    }

    public AddressNotFoundException(String message) {
        super(message);
    }

    public AddressNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddressNotFoundException(Throwable cause) {
        super(cause);
    }

}
