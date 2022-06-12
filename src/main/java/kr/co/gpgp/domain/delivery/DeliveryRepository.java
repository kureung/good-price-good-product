package kr.co.gpgp.domain.delivery;

import java.util.List;
import java.util.Optional;
import kr.co.gpgp.domain.delivery.dto.DeliveryResponse;
import org.springframework.stereotype.Repository;


public interface DeliveryRepository {

    List<Delivery> findByUserId(Long userId);

    Optional<Delivery> findById(Long deliveryId);

    Delivery save(Delivery delivery2);

}
