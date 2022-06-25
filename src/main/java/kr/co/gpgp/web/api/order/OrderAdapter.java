package kr.co.gpgp.web.api.order;

import static java.util.stream.Collectors.toList;

import java.util.List;
import kr.co.gpgp.domain.item.Item;
import kr.co.gpgp.domain.item.ItemFindService;
import kr.co.gpgp.domain.order.Order;
import kr.co.gpgp.domain.orderline.OrderLine;
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
                .itemPrice(orderLine.getPrice())
                .orderQuantity(orderLine.getOrderQuantity())
                .build();
    }

}
