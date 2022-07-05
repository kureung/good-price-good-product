package kr.co.gpgp.domain.courier;

public enum CourierStatus {
    /** 배송대기 상태 */
    WAITING_FOR_DELIVERY("배송대기"),
    /** 배송중 상태 */
    shipping("배송중"),

    /** 배송완료 상태 */
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

    CourierStatus stateChange() {
        if (this == WAITING_FOR_DELIVERY) {
            return shipping;
        }
        return WAITING_FOR_DELIVERY;
    }
    CourierStatus completion() {
        return COMPLETION;
    }

}
