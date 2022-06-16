package kr.co.gpgp.repository.order;

import kr.co.gpgp.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {

}
