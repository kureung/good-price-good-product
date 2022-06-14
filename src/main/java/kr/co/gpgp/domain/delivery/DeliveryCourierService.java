package kr.co.gpgp.domain.delivery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryCourierService {

    private final DeliveryRepository deliveryRepository;


    public void nextStepFinalDelivery(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new IllegalArgumentException("배송 고유 ID 값을 조회 할수 없어 배송완료 상태로 변경할수 없습니다."));
        delivery.nextStepFinalDelivery();
    }


    public void nextStepInTransit(Long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new IllegalArgumentException("배송 고유 ID 값을 조회 할수 없어 배송중 상태로 변경할수 없습니다."));
        delivery.nextStepInstruct();

    }
}