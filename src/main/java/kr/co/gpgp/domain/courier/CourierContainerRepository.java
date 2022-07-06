package kr.co.gpgp.domain.courier;

import java.util.List;
import java.util.Optional;

public interface CourierContainerRepository {

    void save(CourierContainer courierContainer);

    List<CourierContainer> findByCourierList(Courier courier);

    Optional<CourierContainer> findById(Long id);

}
