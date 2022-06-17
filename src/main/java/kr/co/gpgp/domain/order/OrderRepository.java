package kr.co.gpgp.domain.order;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepository {

    Page<Order> orderSearch(OrderSearchCondition condition, Pageable pageable);

    Order save(Order order);

    Optional<Order> findById(Long id);

    List<Order> findByDeliveryId(Long deliveryId);

}
