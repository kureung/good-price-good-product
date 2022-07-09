package kr.co.gpgp.domain.courier;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourierService {

    private final CourierContainerService courierContainerService;
    private final CourierRepository courierRepository;

    public Courier deliverying(Long userId) {
        return findCourier(userId).deliverying();
    }

    public Courier completion(Long userId) {
        return findCourier(userId).completion();
    }

    public Courier waiting(Long userId) {
        return findCourier(userId).waiting();
    }

    private Courier findCourier(Long userId) {
        return courierRepository.findByUserId(userId)
                .orElseThrow(CourierNotFoundException::new);
    }

}
