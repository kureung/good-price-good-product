package kr.co.gpgp.domain.courier;

public enum CourierStatus {
    WAITING_FOR_DELIVERY("배송대기"),
    SHIPPING("배송중"),
    COMPLETION("배송완료");

    private String message;

    CourierStatus(String message) {
        this.message = message;
    }

    public static CourierStatus init() {
        return WAITING_FOR_DELIVERY;
    }

    private String getMessage() {
        return message;
    }

}
