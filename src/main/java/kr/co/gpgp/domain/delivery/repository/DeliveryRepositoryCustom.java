package kr.co.gpgp.domain.delivery.repository;

import java.util.List;
import kr.co.gpgp.domain.delivery.dto.DeliveryResponse;

public interface DeliveryRepositoryCustom {
    List<DeliveryResponse> findByUserId(Long userId);
}
