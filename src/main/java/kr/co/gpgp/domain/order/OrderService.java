package kr.co.gpgp.domain.order;

import java.util.List;
import java.util.NoSuchElementException;
import kr.co.gpgp.domain.delivery.Delivery;
import kr.co.gpgp.domain.item.ItemFindService;
import kr.co.gpgp.domain.orderline.OrderLine;
import kr.co.gpgp.domain.user.User;
import kr.co.gpgp.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ItemFindService itemFindService;

    @Transactional
    public Long order(Long userId, Delivery delivery, List<OrderLine> orderLines) {
        if (orderLines.isEmpty()) {
            throw new IllegalArgumentException("주문하려는 상품을 입력해주세요.");
        }

        User user = userService.findOne(userId);

        Order order = Order.of(user, delivery, orderLines);
        return orderRepository.save(order).getId();
    }

    @Transactional
    public void cancel(Long orderId) {
        Order order = findOne(orderId);
        order.cancel();

        Delivery delivery = order.getDelivery();
        delivery.cancel();
    }

    public Order findOne(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("해당 주문을 찾을 수 없습니다."));
    }

    public Page<OrderSearchResponse> searchOrder(OrderSearchCondition condition, Pageable pageable) {
        return orderRepository.orderSearch(condition, pageable);

    }

}