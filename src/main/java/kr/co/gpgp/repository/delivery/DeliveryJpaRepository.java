package kr.co.gpgp.repository.delivery;

import kr.co.gpgp.domain.delivery.Delivery;
import kr.co.gpgp.domain.delivery.DeliveryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryJpaRepository  extends JpaRepository<Delivery,Long> {

}
