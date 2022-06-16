package kr.co.gpgp.repository.order;

import kr.co.gpgp.domain.order.OrderSearchCondition;
import kr.co.gpgp.domain.order.OrderSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {

    Page<OrderSearchResponse> orderSearch(OrderSearchCondition condition, Pageable pageable);

}
