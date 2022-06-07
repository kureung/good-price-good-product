package kr.co.gpgp.domain.delivery.entity.enums;


import java.util.EnumMap;
import kr.co.gpgp.domain.delivery.exception.DeliveryStatusOverflowException;

public enum DeliveryStatusImpl implements DeliveryStatus {

    ACCEPT {
        public String get()  { return statusMessage.get(this); }
        public DeliveryStatusImpl next() { return sequence.get(this); }
    },
    INSTRUCT {
        public String get()  { return statusMessage.get(this); }
        public DeliveryStatusImpl next() { return sequence.get(this); }
    },
    DEPARTURE {
        public String get()  { return statusMessage.get(this); }
        public DeliveryStatusImpl next() { return sequence.get(this); }
    },
    FINAL_DELIVERY {
        public String get()  { return statusMessage.get(this); }
        public DeliveryStatusImpl next() { return sequence.get(this); }
    },
    NONE_TRACKING {
        public String get()  { return statusMessage.get(this); }
        public DeliveryStatusImpl next() {
            throw new DeliveryStatusOverflowException("이미 완료된 배송입니다.");
        }
    };
    private static final EnumMap<DeliveryStatusImpl, DeliveryStatusImpl> sequence = new EnumMap<>(DeliveryStatusImpl.class);
    private static final EnumMap<DeliveryStatusImpl, String> statusMessage = new EnumMap<>(DeliveryStatusImpl.class);

    /** 배송 상태 순서 불멸 저장<br>
     * <br>
     * put(현재상태,다음상태)<br>
     * get(현재상태) -> 다음 상태 값 블러오기 <br>
     *
     */
    public static final void sequenceInit() {
        sequence.put(ACCEPT, INSTRUCT);
        sequence.put(INSTRUCT, DEPARTURE);
        sequence.put(DEPARTURE, FINAL_DELIVERY);
        sequence.put(FINAL_DELIVERY, NONE_TRACKING);
    }
    public static final void statusMessageInit() {
        statusMessage.put(ACCEPT, "결제완료");
        statusMessage.put(INSTRUCT, "상품준비중");
        statusMessage.put(DEPARTURE, "배송지시");
        statusMessage.put(FINAL_DELIVERY, "배송중");
        statusMessage.put(NONE_TRACKING, "배송완료");
    }

}