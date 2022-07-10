package kr.co.gpgp.domain.courier;

import java.util.List;
import java.util.Optional;

public interface CourierRepository {

    void save(Courier courier);

    Optional<Courier> findByUserId(Long id);

    List<Courier> findByCourierArea(CourierArea courierArea);

}
