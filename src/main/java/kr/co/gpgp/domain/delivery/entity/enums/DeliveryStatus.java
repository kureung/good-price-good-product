package kr.co.gpgp.domain.delivery.entity.enums;


import java.util.EnumMap;
import kr.co.gpgp.domain.delivery.exception.DeliveryStatusAuthException;
import kr.co.gpgp.domain.delivery.exception.DeliveryStatusOverflowException;
import kr.co.gpgp.domain.user.entity.Role;

public enum DeliveryStatus {
    ACCEPT,
    INSTRUCT,
    DEPARTURE,
    FINAL_DELIVERY,
    NONE_TRACKING{
        @Override
        public DeliveryStatus next(Role role)  { //  - endPoint
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

    /** next() 배송 상태 변경 권한
      *
      */
    private static void authInit() {
        // 회원의 권한
        auth.put(ACCEPT, Role.USER);

        // 판매자의 권한
        auth.put(INSTRUCT, Role.SELLER);
        auth.put(DEPARTURE, Role.SELLER);

        // 택배원의 권한
        auth.put(FINAL_DELIVERY, Role.COURIER);
        auth.put(NONE_TRACKING,  Role.COURIER);
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
    public DeliveryStatus next(Role role) {
        if( auth.get(this) == role){
            return sequence.get(this);
        }
        throw new DeliveryStatusAuthException("권한이 없어 배송상태를 변경하지 못합니다.");
    }

    private static final EnumMap<DeliveryStatus, DeliveryStatus> sequence = new EnumMap<>(DeliveryStatus.class);
    private static final EnumMap<DeliveryStatus, String> statusMessage = new EnumMap<>(DeliveryStatus.class);
    private static final EnumMap<DeliveryStatus, Role> auth = new EnumMap<>(DeliveryStatus.class);


    public static DeliveryStatus init() {
        sequenceInit();
        statusMessageInit();
        authInit();
        return DeliveryStatus.ACCEPT;
    }


}