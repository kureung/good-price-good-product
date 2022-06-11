package kr.co.gpgp.domain.order.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.NoSuchElementException;
import kr.co.gpgp.domain.address.entity.Address;
import kr.co.gpgp.domain.delivery.entity.Delivery;
import kr.co.gpgp.domain.item.Item;
import kr.co.gpgp.domain.item.ItemFindService;
import kr.co.gpgp.domain.order.dto.OrderRequest;
import kr.co.gpgp.domain.order.dto.OrderRequest.OrderLineRequest;
import kr.co.gpgp.domain.order.dto.OrderResponse;
import kr.co.gpgp.domain.order.dto.OrderResponse.OrderLineResponse;
import kr.co.gpgp.domain.order.Order;
import kr.co.gpgp.repository.order.OrderJpaRepository;
import kr.co.gpgp.domain.orderline.entity.OrderLine;
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

    private final OrderJpaRepository orderJpaRepository;
    private final UserService userService;
    private final ItemFindService itemFindService;

    @Transactional
    public Long order(Long userId, Delivery delivery, List<OrderLineRequest> orderLineRequests) {
        if (orderLineRequests.isEmpty()) {
            throw new IllegalArgumentException("주문하려는 상품을 입력해주세요.");
        }

        User user = userService.findOne(userId);
        List<OrderLine> orderLines = this.toEntities(orderLineRequests);

        Order order = Order.of(user, delivery, orderLines);
        return orderJpaRepository.save(order).getId();
    }

    @Transactional
    public OrderResponse register(Long userId, OrderRequest request) {
        User user = userService.findOne(userId);
        Address address = Address.of(
                user,
                request.getRoadName(),
                request.getZipCode(),
                request.getAddressName(),
                request.getDetailedAddress());

        Requirement requirement = new Requirement(request.getRequirement());
        Delivery delivery = Delivery.of(requirement, address);

        Long orderId = order(userId, delivery, request.getOrderLines());
        Order order = findOne(orderId);

        return OrderResponse.builder()
                .id(orderId)
                .roadName(order.getAddressRoadName())
                .requirement(order.getRequirementMessage())
                .detailedAddress(order.getDetailedAddress())
                .zipCode(order.getAddressZipCode())
                .addressName(order.getAddressName())
                .orderLines(toDtos(order.getOrderLines()))
                .build();
    }

    @Transactional
    public void cancel(Long orderId) {
        Order order = findOne(orderId);
        order.cancel();
    }

    public Order findOne(Long orderId) {
        return orderJpaRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException("해당 주문을 찾을 수 없습니다."));
    }

    public OrderResponse findOneToDto(Long orderId) {
        Order order = findOne(orderId);

        return OrderResponse.builder()
                .id(orderId)
                .roadName(order.getAddressRoadName())
                .requirement(order.getRequirementMessage())
                .detailedAddress(order.getDetailedAddress())
                .zipCode(order.getAddressZipCode())
                .addressName(order.getAddressName())
                .orderLines(toDtos(order.getOrderLines()))
                .build();

    }

    public List<OrderLine> toEntities(List<OrderLineRequest> requests) {
        return requests.stream()
                .map(this::toEntity)
                .collect(toList());
    }

    private OrderLine toEntity(OrderLineRequest request) {
        String itemCode = request.getItemCode();
        Item findItem = itemFindService.findOne(itemCode);
        return OrderLine.of(findItem, request.getOrderQuantity());
    }

    public List<OrderLineResponse> toDtos(List<OrderLine> orderLines) {
        return orderLines.stream()
                .map(this::toDto)
                .collect(toList());
    }

    private OrderLineResponse toDto(OrderLine orderLine) {
        return OrderLineResponse.builder()
                .itemCode(orderLine.getItemCode())
                .itemPrice(orderLine.getPrice())
                .orderQuantity(orderLine.getOrderQuantity())
                .itemName(orderLine.getItemName())
                .build();
    }

}