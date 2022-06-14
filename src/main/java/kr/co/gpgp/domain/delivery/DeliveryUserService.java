package kr.co.gpgp.domain.delivery;

import java.util.List;
import kr.co.gpgp.domain.user.UserRepository;
import kr.co.gpgp.repository.delivery.DeliveryRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryUserService {

    private final DeliveryRepositoryImpl deliveryRepository;
    private final UserRepository userRepository;


    //유저는 자신의 모든 배송 을 조회를 할수 있다.
    public List<Delivery> selectAll(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID가 없어 배송을 조회할수 없습니다."));

        return deliveryRepository.findByUserId(userId);

    }

    //유저는 자신의 배송 을 조회를 할수 있다.
    public Delivery select(Long userId, Long deliveryId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 ID가 없어 배송을 조회할수 없습니다."));

        Delivery delivery = deliveryRepository.findById(deliveryId)
                .filter(s -> s.getUserId() == userId)
                .orElseThrow(() -> new IllegalArgumentException("선택한 배송은 유저가 생성한게 아닙니다."));

        return delivery;
    }

    //유저는 배송완료 상태를 구매확정 상태로 바꿀수있다.
    public void nextStepPurchaseConfirmation(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new IllegalArgumentException(""));
        delivery.nextStepPurchaseConfirmation();
    }


}
