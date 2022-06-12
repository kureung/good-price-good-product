package kr.co.gpgp.repository.delivery;

import java.util.List;
import kr.co.gpgp.domain.delivery.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryJpaRepository  extends JpaRepository<Delivery,Long> {

    List<Delivery> findByAddressUserId(Long userId);

}
