package kr.co.gpgp.domain.delivery;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository {

    List<Delivery> findByUserId(Long userId);

    Optional<Delivery> findById(Long deliveryId);

    Delivery save(Delivery delivery2);

    void delete(Delivery Delivery);

}
