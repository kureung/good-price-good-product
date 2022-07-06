package kr.co.gpgp.repository.courier;

import java.util.List;
import kr.co.gpgp.domain.courier.Courier;
import kr.co.gpgp.domain.courier.CourierArea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierJpaRepository extends JpaRepository<Courier, Long> {

    Courier findByUserId(Long id);

    List<Courier> findByCourierArea(CourierArea courierArea);

}
