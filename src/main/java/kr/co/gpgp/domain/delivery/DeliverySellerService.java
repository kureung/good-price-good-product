package kr.co.gpgp.domain.delivery;

import kr.co.gpgp.repository.delivery.DeliveryRepositoryImpl;
import kr.co.gpgp.repository.user.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliverySellerService implements DeliveryService {

    private final DeliveryRepositoryImpl deliveryRepository;
    private final UserRepositoryImpl userRepository;

    //    DeliverySellerService.sequence.instructStep(Target DeliveryId,User);

    //      [다른 서비스에서 사용될 배송 상태]
    // 1. (회원 시점) 결제를 하면 배송상태가 생긴다.
    //  1-1 (회원 시점) 결제완료 상태에서 주문취소가 가능하다 ."취소" 상태로 바뀔수 있다.
    // 2. (판매자 시점) 회원이 결제한 주문을 "상품준비중" 으로 상태변경을 할수있다.
    //  2-1 (판매자 시점) 회원이 결제한 주문을 "취소"으로 상태 변경을 할수 있다.
    // 3. ( 판매자 시점) 회원이 결제한 주문을 "상품중비중"-> "배송지시" 으로 변경할수 있다.
    //  3-1 (판매자 시점) 회원이 결제한 주문을 "취소"으로 상태 변경을 할수 있다.

    // 4. (택배원 시점 ) 인계받은 회원 의 배송상태를 "배송지시" -> "배송중" 으로 변경할수 있다.
    //  4-1 (택배원 시점) "배송완료" 부터 상태 변경을 할수 없다.
    // 5. (택배원 시점 ) 인계받은 회원 의 배송상태를 "배송중" -> "배송완료" 으로 변경할수 있다.
    //  5-1 (택배원 시점) "배송완료" 부터 상태 변경을 할수 없다.

    // TODO : 상태 취소 서비스
    // TODO : 다음 상태 로 값 변경 서비스
    //[결제완료,상품준비중] 단계에서 배송은 취소 될수있다.

    @Override
    public void cancelStatus() {

    }

    @Override
    public void sequenceNextStatus() {

    }

}
