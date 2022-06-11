package kr.co.gpgp.repository.delivery;

import java.util.List;
import kr.co.gpgp.domain.delivery.dto.DeliveryResponse;

public interface DeliveryRepositoryCustom {

    List<DeliveryResponse> findByUserId(Long userId);

}
