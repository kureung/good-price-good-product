package kr.co.gpgp.repository.delivery;

import java.util.List;
import java.util.Optional;
import kr.co.gpgp.domain.delivery.Delivery;
import kr.co.gpgp.domain.delivery.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeliveryRepositoryImpl implements DeliveryRepository {

    private final DeliveryJpaRepository jpaRepository;

    @Override
    public List<Delivery> findByUserId(Long userId) {
        return jpaRepository.findByAddressUserId(userId);
    }

    @Override
    public Optional<Delivery> findById(Long deliveryId) {
        return jpaRepository.findById(deliveryId);
    }

    public void save(Delivery delivery) {
        jpaRepository.save(delivery);
    }

}
