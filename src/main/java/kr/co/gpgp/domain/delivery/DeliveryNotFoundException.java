package kr.co.gpgp.domain.delivery;

public class DeliveryNotFoundException extends RuntimeException {

    public DeliveryNotFoundException() {
        super("배송을 찾을수 없습니다.");
    }

}
