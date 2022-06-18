package kr.co.gpgp.domain.order;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.address.AddressRepository;
import kr.co.gpgp.domain.address.AddressService;
import kr.co.gpgp.domain.delivery.Delivery;
import kr.co.gpgp.domain.orderline.OrderLine;
import kr.co.gpgp.domain.requirement.Requirement;
import kr.co.gpgp.domain.requirement.RequirementRepository;
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
    private final AddressService addressService;

    private final AddressRepository addressRepository;

    private final RequirementRepository requirementRepository;

    @Transactional
    public Long order(Long userId,
                      Address address,
                      Requirement requirement,
                      List<OrderLine> orderLines
    ) {

        Address findAddress = addressRepository.findByName(address.getName())
                .orElseGet(() -> addressRepository.save(address));

        Requirement findRequirement = requirementRepository.findByMessage(requirement.getMessage())
                .orElseGet(() -> requirementRepository.save(requirement));

        User user = userService.findOne(userId);

        Delivery delivery = Delivery.of(findRequirement, findAddress);

        String orderCode = UUID.randomUUID().toString();

        Order order = Order.of(user, delivery, orderLines, orderCode);
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