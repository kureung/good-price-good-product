package kr.co.gpgp.domain.delivery.entity.enums;

import java.util.EnumMap;
import java.util.List;
import kr.co.gpgp.domain.delivery.exception.DeliveryStatusAuthException;
import kr.co.gpgp.domain.delivery.exception.DeliveryStatusOverflowException;
import kr.co.gpgp.domain.user.entity.Role;
import kr.co.gpgp.domain.user.entity.User;

public enum DeliveryStatus {

    /**
     * 결제 완료 상태
     */
    ACCEPT,
    /**
     * 상품 중비중 상태
     */
    INSTRUCT,
    /**
     * 배송 지시 상태
     */
    DEPARTURE,
    /**
     * 배송 중 상태
     */
    FINAL_DELIVERY,
    /**
     * 배송 완료 상태
     */
    NONE_TRACKING {
        @Override
        public DeliveryStatus next(User user) { //  - delivery endPoint
            throw new DeliveryStatusOverflowException("이미 완료된 배송입니다.");
        }
    },
    /**
     * 구매 확정 상태
     */
    PURCHASE_CONFIRMATION,

    /**
     * 배송 취소 상태
     */
    WITHDRAW_ORDER;

    public static DeliveryStatus init() {
        return DeliveryStatus.ACCEPT;
    }

    /**
     * 현재 상태를 메시지로 리턴
     *
     * @return
     */
    public String getMessage() {
        return statusMessage.get(this);
    }

    /**
     * 현재 상태를 다음 상태로 변경함
     *
     * @return
     */
    public DeliveryStatus next(User user) {
        auth.get(user.getRole())                        // 유저의 권한확인 ( 회원,판매자,배달원,관리자)
                .stream().filter(v -> v==this)  // 권한이 있을시 나감
                .findFirst()                            // 권한이 없으면 아래 실행
                .orElseThrow(() -> new DeliveryStatusAuthException("권한이 없어 배송상태를 변경하지 못합니다."));
        return sequence.get(this);
    }

    public void cancelStatus(User user) {
        auth.get(user.getRole())                        // 유저의 권한확인 ( 회원,판매자,배달원,관리자)
                .stream().filter(v -> v==this && v==WITHDRAW_ORDER)  // 권한이 있을시 나감
                .findFirst()                            // 권한이 없으면 아래 실행
                .orElseThrow(() -> new DeliveryStatusAuthException("권한이 없어 배송상태를 변경하지 못합니다."));

    }

    private static final EnumMap<DeliveryStatus, DeliveryStatus> sequence = sequenceInit();
    private static final EnumMap<DeliveryStatus, String> statusMessage = statusMessageInit();
    private static final EnumMap<Role, List<DeliveryStatus>> auth = authInit();

    /**
     * 배송 상태별 다음 상태 저장
     *
     * <br>
     * put(현재상태,다음상태)<br>
     * get(현재상태) -> 다음 상태 값 블러오기 <br>
     */
    private static EnumMap<DeliveryStatus, DeliveryStatus> sequenceInit() {
        EnumMap<DeliveryStatus, DeliveryStatus> sequence = new EnumMap<DeliveryStatus, DeliveryStatus>(DeliveryStatus.class);

        sequence.put(ACCEPT, INSTRUCT);
        sequence.put(INSTRUCT, DEPARTURE);
        sequence.put(DEPARTURE, FINAL_DELIVERY);
        sequence.put(FINAL_DELIVERY, NONE_TRACKING);
        sequence.put(NONE_TRACKING, PURCHASE_CONFIRMATION);

        return sequence;
    }

    /**
     * 상태별 메시지
     *
     * <br>
     * put(상태,상태메시지) -   > 상태 마다 메시지를 저장함
     * get(상태)             -> 상태 마다 메시지를 나타냄
     */
    private static EnumMap<DeliveryStatus, String> statusMessageInit() {
        EnumMap<DeliveryStatus, String> statusMessage = new EnumMap<DeliveryStatus, String>(DeliveryStatus.class);

        statusMessage.put(ACCEPT, "결제완료");
        statusMessage.put(INSTRUCT, "상품준비중");
        statusMessage.put(DEPARTURE, "배송지시");
        statusMessage.put(FINAL_DELIVERY, "배송중");
        statusMessage.put(NONE_TRACKING, "배송완료");
        statusMessage.put(PURCHASE_CONFIRMATION, "구매확정");

        //특이 상태
        statusMessage.put(WITHDRAW_ORDER, "주문취소");

        return statusMessage;
    }

    /**
     * 배송 상태 변경 권한<br>
     * <br>
     * 변경 가능성이 커도 외부 코드에 영향을 주지 않기떄문에<br>
     * 권한 추가 삭제 수정이 자유럽다.<br>
     */
    private static EnumMap<Role, List<DeliveryStatus>> authInit() {
        EnumMap<Role, List<DeliveryStatus>> auth = new EnumMap<Role, List<DeliveryStatus>>(Role.class);

        // 판매자의 권한
        auth.put(Role.SELLER,
                List.of(
                        ACCEPT,
                        INSTRUCT,
                        WITHDRAW_ORDER
                )
        );

        // 택배원의 권한
        auth.put(Role.COURIER,
                List.of(
                        DEPARTURE,
                        FINAL_DELIVERY
                )
        );

        //유저의 권한
        auth.put(Role.USER,
                List.of(
                        WITHDRAW_ORDER,
                        WITHDRAW_ORDER
                )
        );

        return auth;
    }

}