package kr.co.gpgp.repository.delivery;

import java.util.List;
import java.util.Optional;
import kr.co.gpgp.domain.delivery.Delivery;
import kr.co.gpgp.domain.delivery.DeliveryRepository;
import kr.co.gpgp.domain.delivery.dto.DeliveryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeliveryRepositoryImpl implements DeliveryRepository {

    private final DeliveryJpaRepository jpaRepository;
    private final DeliveryRepositoryCustom repositoryCustom;

    @Override
    public List<DeliveryResponse> findByUserId(Long userId) {
        return repositoryCustom.findByUserId(userId);
    }

    @Override
    public Optional<Delivery> findById(Long deliveryId) {
        return jpaRepository.findById(deliveryId);
    }

    public void save(Delivery delivery) {
        jpaRepository.save(delivery);
    }

}
