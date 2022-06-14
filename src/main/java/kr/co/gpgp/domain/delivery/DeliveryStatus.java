package kr.co.gpgp.domain.delivery;

import java.util.EnumMap;

public enum DeliveryStatus {

    /** 결제 완료 상태 */
    ACCEPT,
    /** 상품 중비중 상태 */
    INSTRUCT,
    /** 배송 지시 상태 */
    DEPARTURE,
    /** 배송 중 상태 */
    IN_TRANSIT,
    /** 배송 완료 상태 */
    FINAL_DELIVERY,
    /** 구매 확정 상태 */
    PURCHASE_CONFIRMATION,
    /** 배송 취소 상태 */
    WITHDRAW_ORDER;

    public static DeliveryStatus init() {
        return ACCEPT;
    }

    public String getMessage() {
        return statusMessage.get(this);
    }

    DeliveryStatus cancelStatus() {
        return WITHDRAW_ORDER;
    }

    static final EnumMap<DeliveryStatus, DeliveryStatus> sequence = sequenceInit();
    static final EnumMap<DeliveryStatus, String> statusMessage = statusMessageInit();


    private static EnumMap<DeliveryStatus, DeliveryStatus> sequenceInit() {
        EnumMap<DeliveryStatus, DeliveryStatus> sequence = new EnumMap<>(DeliveryStatus.class);

        sequence.put(ACCEPT, INSTRUCT);
        sequence.put(INSTRUCT, DEPARTURE);
        sequence.put(DEPARTURE, IN_TRANSIT);
        sequence.put(IN_TRANSIT, FINAL_DELIVERY);
        sequence.put(FINAL_DELIVERY, PURCHASE_CONFIRMATION);

        return sequence;
    }

    private static EnumMap<DeliveryStatus, String> statusMessageInit() {
        EnumMap<DeliveryStatus, String> statusMessage = new EnumMap<>(DeliveryStatus.class);

        statusMessage.put(ACCEPT, "결제완료");
        statusMessage.put(INSTRUCT, "상품준비중");
        statusMessage.put(DEPARTURE, "배송지시");
        statusMessage.put(IN_TRANSIT, "배송중");
        statusMessage.put(FINAL_DELIVERY, "배송완료");
        statusMessage.put(PURCHASE_CONFIRMATION, "구매확정");
        statusMessage.put(WITHDRAW_ORDER, "배송취소");

        return statusMessage;
    }

}