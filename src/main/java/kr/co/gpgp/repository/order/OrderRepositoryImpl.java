package kr.co.gpgp.repository.order;

import java.util.Optional;
import kr.co.gpgp.domain.order.Order;
import kr.co.gpgp.domain.order.OrderRepository;
import kr.co.gpgp.domain.order.OrderSearchCondition;
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

    @Override
    public Order save(Order order) {
        return jpaRepository.save(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return jpaRepository.findById(id);
    }

}
