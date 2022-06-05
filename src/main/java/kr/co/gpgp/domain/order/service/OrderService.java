package kr.co.gpgp.domain.order.service;

import java.util.List;
import java.util.NoSuchElementException;
import kr.co.gpgp.domain.address.entity.Address;
import kr.co.gpgp.domain.delivery.entity.Delivery;
import kr.co.gpgp.domain.order.entity.Order;
import kr.co.gpgp.domain.order.repository.OrderRepository;
import kr.co.gpgp.domain.orderline.dto.OrderLineRequest;
import kr.co.gpgp.domain.orderline.entity.OrderLine;
import kr.co.gpgp.domain.orderline.service.OrderLineDtoService;
import kr.co.gpgp.domain.requirement.entity.Requirement;
import kr.co.gpgp.domain.user.entity.User;
import kr.co.gpgp.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderLineDtoService orderLineDtoService;
    private final OrderRepository orderRepository;
    private final UserService userService;

    @Transactional
    public Long order(Long userId, Requirement requirement, Address address, List<OrderLineRequest> orderLineRequests) {

        if (orderLineRequests.size() == 0) {
            throw new IllegalArgumentException("주문하려는 상품을 입력해주세요.");
        }

        User user = userService.findOne(userId);

        Delivery delivery = Delivery.of(requirement, address);

        List<OrderLine> orderLines = orderLineDtoService.toEntity(orderLineRequests);

        Order order = Order.of(user, delivery, orderLines);
        orderRepository.save(order);

        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = findOne(orderId);
        order.cancel();
    }

    public Order findOne(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("해당 주문을 찾을 수 없습니다."));
    }
}