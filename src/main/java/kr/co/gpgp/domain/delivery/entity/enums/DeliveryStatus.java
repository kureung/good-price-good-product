package kr.co.gpgp.domain.delivery.entity.enums;


import java.util.EnumMap;
import kr.co.gpgp.domain.delivery.exception.DeliveryStatusOverflowException;

public enum DeliveryStatus {
    ACCEPT,
    INSTRUCT,
    DEPARTURE,
    FINAL_DELIVERY,
    NONE_TRACKING{
        @Override
        public DeliveryStatus next()  { //  - endPoint
            throw new DeliveryStatusOverflowException("이미 완료된 배송입니다.");
        }
    };

    /**
     * 배송 상태별 다음 상태 저장
     *
     * <br>
     * put(현재상태,다음상태)<br>
     * get(현재상태) -> 다음 상태 값 블러오기 <br>
     */
    private static void sequenceInit() {
        sequence.put(ACCEPT, INSTRUCT);
        sequence.put(INSTRUCT, DEPARTURE);
        sequence.put(DEPARTURE, FINAL_DELIVERY);
        sequence.put(FINAL_DELIVERY, NONE_TRACKING);
    }

    /**
     * 상태별 메시지
     *
     * <br>
     * put(상태,상태메시지) -   > 상태 마다 메시지를 저장함
     * get(상태)             -> 상태 마다 메시지를 나타냄
     */
    private static void statusMessageInit() {
        statusMessage.put(ACCEPT, "결제완료");
        statusMessage.put(INSTRUCT, "상품준비중");
        statusMessage.put(DEPARTURE, "배송지시");
        statusMessage.put(FINAL_DELIVERY, "배송중");
        statusMessage.put(NONE_TRACKING, "배송완료");
    }


    /** 현재 상태를 메시지로 리턴
     *
     * @return
     */
    public String getMessage() {
        return statusMessage.get(this);
    }

    /** 현재 상태를 다음 상태로 변경함
     *
     * @return
     */
    public DeliveryStatus next() {
        return sequence.get(this);
    }

    private static final EnumMap<DeliveryStatus, DeliveryStatus> sequence = new EnumMap<>(DeliveryStatus.class);
    private static final EnumMap<DeliveryStatus, String> statusMessage = new EnumMap<>(DeliveryStatus.class);


    public static DeliveryStatus init() {
        sequenceInit();
        statusMessageInit();
        return DeliveryStatus.ACCEPT;
    }


}