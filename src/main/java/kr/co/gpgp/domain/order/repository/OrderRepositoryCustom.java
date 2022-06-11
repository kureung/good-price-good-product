package kr.co.gpgp.domain.order.repository;

import kr.co.gpgp.domain.order.OrderSearchCondition;
import kr.co.gpgp.domain.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {

    Page<Order> orderSearch(OrderSearchCondition condition, Pageable pageable);

}
