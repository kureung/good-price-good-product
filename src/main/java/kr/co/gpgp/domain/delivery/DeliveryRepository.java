package kr.co.gpgp.domain.delivery;

import java.util.List;
import java.util.Optional;
import kr.co.gpgp.domain.delivery.dto.DeliveryResponse;

public interface DeliveryRepository {

    List<DeliveryResponse> findByUserId(Long userId);

    Optional<Delivery> findById(Long deliveryId);

    void save(Delivery delivery2);

}
