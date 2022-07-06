package kr.co.gpgp.domain.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("회원을 찾을수 없습니다.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }

}
