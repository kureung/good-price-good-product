package kr.co.gpgp.domain.delivery.repository;

import kr.co.gpgp.domain.delivery.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery,Long> {

}
