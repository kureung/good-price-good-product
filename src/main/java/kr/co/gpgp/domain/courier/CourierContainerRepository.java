package kr.co.gpgp.domain.courier;

import java.util.List;

public interface CourierContainerRepository {

    void save(CourierContainer courierContainer);

    List<CourierContainer> findByCourier(Courier courier);

}
