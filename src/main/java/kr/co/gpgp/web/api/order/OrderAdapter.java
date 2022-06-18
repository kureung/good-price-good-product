package kr.co.gpgp.web.api.order;

import static java.util.stream.Collectors.toList;

import java.util.List;
import kr.co.gpgp.domain.address.Address;
import kr.co.gpgp.domain.delivery.Delivery;
import kr.co.gpgp.domain.item.Item;
import kr.co.gpgp.domain.item.ItemFindService;
import kr.co.gpgp.domain.order.Order;
import kr.co.gpgp.domain.orderline.OrderLine;
import kr.co.gpgp.domain.requirement.Requirement;
import kr.co.gpgp.domain.user.User;
import kr.co.gpgp.domain.user.UserService;
import kr.co.gpgp.web.api.order.OrderRequest.OrderLineRequest;
import kr.co.gpgp.web.api.order.OrderResponse.OrderLineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderAdapter {

    private final ItemFindService itemFindService;
    private final UserService userService;

    public Order toEntity(OrderRequest orderRequest) {
        User user = userService.findOne(orderRequest.getUserId());
        Requirement requirement = new Requirement(orderRequest.getRequirement());

        Address address = Address.of(user,
                orderRequest.getRoadName(),
                orderRequest.getZipCode(),
                orderRequest.getAddressName(),
                orderRequest.getDetailedAddress());

        Delivery delivery = Delivery.of(requirement, address);

        List<OrderLine> orderLines = orderRequest.getOrderLines()
                .stream()
                .map(this::toEntity)
                .collect(toList());

        return Order.of(user, delivery, orderLines);
    }

    public OrderResponse toDto(Order order) {
        List<OrderLineResponse> orderLineResponses = order.getOrderLines()
                .stream()
                .map(this::toDto)
                .collect(toList());

        return OrderResponse.builder()
                .id(order.getId())
                .requirement(order.getRequirementMessage())
                .roadName(order.getAddressRoadName())
                .zipCode(order.getAddressZipCode())
                .addressName(order.getAddressName())
                .detailedAddress(order.getDetailedAddress())
                .orderLines(orderLineResponses)
                .build();
    }

    public List<OrderLine> toEntities(List<OrderLineRequest> orderLineRequests) {
        return orderLineRequests.stream()
                .map(this::toEntity)
                .collect(toList());
    }

    public List<OrderLineResponse> toDtos(List<OrderLine> orderLines) {
        return orderLines.stream()
                .map(this::toDto)
                .collect(toList());
    }

    private OrderLine toEntity(OrderLineRequest orderLineRequest) {
        String itemCode = orderLineRequest.getItemCode();
        Item item = itemFindService.findOne(itemCode);
        return OrderLine.of(item, orderLineRequest.getOrderQuantity());
    }

    private OrderLineResponse toDto(OrderLine orderLine) {
        return OrderLineResponse.builder()
                .itemName(orderLine.getItemName())
                .itemCode(orderLine.getItemCode())
                .itemPrice(orderLine.getPrice())
                .orderQuantity(orderLine.getOrderQuantity())
                .build();
    }

}
