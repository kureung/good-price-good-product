package kr.co.gpgp.domain.delivery;

import java.util.EnumMap;

public enum DeliveryStatus {

    /** 결제 완료 상태 */
    ACCEPT("결제완료"),
    /** 상품 중비중 상태 */
    INSTRUCT("상품준비중"),
    /** 배송 지시 상태 */
    DEPARTURE("배송지시"),
    /** 배송 중 상태 */
    IN_TRANSIT( "배송중"),
    /** 배송 완료 상태 */
    FINAL_DELIVERY("배송완료"),
    /** 구매 확정 상태 */
    PURCHASE_CONFIRMATION( "구매확정"),
    /** 배송 취소 상태 */
    WITHDRAW_ORDER( "배송취소");
    private String message;
    DeliveryStatus(String message) {
        this.message=message;
    }
    public static DeliveryStatus init() {
        return ACCEPT;
    }

    public String getMessage() {
        return message;
    }

    DeliveryStatus cancelStatus() {
        return WITHDRAW_ORDER;
    }

    static final EnumMap<DeliveryStatus, DeliveryStatus> sequence = sequenceInit();

    private static EnumMap<DeliveryStatus, DeliveryStatus> sequenceInit() {
        EnumMap<DeliveryStatus, DeliveryStatus> sequence = new EnumMap<>(DeliveryStatus.class);

        sequence.put(ACCEPT, INSTRUCT);
        sequence.put(INSTRUCT, DEPARTURE);
        sequence.put(DEPARTURE, IN_TRANSIT);
        sequence.put(IN_TRANSIT, FINAL_DELIVERY);
        sequence.put(FINAL_DELIVERY, PURCHASE_CONFIRMATION);

        return sequence;
    }

}