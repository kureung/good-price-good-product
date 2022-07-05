package kr.co.gpgp.domain.courier;

import java.util.List;

public interface CourierRepository {

    void save(Courier courier);

    Courier findByUserId(Long id);

    List<Courier> findByCourierArea(CourierArea courierArea);

}
