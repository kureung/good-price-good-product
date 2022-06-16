package kr.co.gpgp.domain.delivery;

import java.util.List;
import kr.co.gpgp.domain.user.UserRepository;
import kr.co.gpgp.repository.delivery.DeliveryRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        return deliveryRepository.findById(deliveryId)
                .filter(s -> s.getUserId() == userId)
                .orElseThrow(() -> new IllegalArgumentException("선택한 배송은 유저가 생성한게 아닙니다."));
    }

    //유저는 배송완료 상태를 구매확정 상태로 바꿀수있다.
    public void nextStepPurchaseConfirmation(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new IllegalArgumentException("배송 ID 를 조회 할수 없습니다."));
        delivery.nextStepPurchaseConfirmation();
    }

    @Transactional
    public void save(Delivery delivery) {
        deliveryRepository.save(delivery);
    }



    public void delete(Delivery delivery) {
        Delivery findDelivery = deliveryRepository.findById(delivery.getId())
                .orElseThrow(() -> new IllegalArgumentException("배송 ID가 존재하지 않아 삭제할수 없습니다."));
        deliveryRepository.delete(findDelivery);
    }

    public Delivery update(Delivery delivery) {
        Delivery findDelivery = deliveryRepository.findById(delivery.getId())
                .orElseThrow(() -> new IllegalArgumentException("배송 ID가 존재하지 않아 수정할수 없습니다."));
        return deliveryRepository.save(findDelivery);
    }
}
