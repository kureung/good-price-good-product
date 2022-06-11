package kr.co.gpgp.repository.order;

import kr.co.gpgp.domain.order.OrderRepository;
import kr.co.gpgp.domain.order.OrderSearchCondition;
import kr.co.gpgp.domain.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository jpaRepository;
    private final OrderRepositoryCustom repositoryCustom;

    @Override
    public Page<Order> orderSearch(OrderSearchCondition condition, Pageable pageable) {
        return repositoryCustom.orderSearch(condition, pageable);
    }

}
