package kr.co.gpgp.repository.courier;

import java.util.List;
import kr.co.gpgp.domain.courier.Courier;
import kr.co.gpgp.domain.courier.CourierContainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierContainerJpaRepository extends JpaRepository<CourierContainer, Long> {

    List<CourierContainer> findByCourier(Courier courier);

}
