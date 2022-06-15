package kr.co.gpgp.domain.order;

import com.querydsl.core.annotations.QueryProjection;
import java.util.ArrayList;
import java.util.List;
import kr.co.gpgp.domain.delivery.DeliveryStatus;
import lombok.Getter;

@Getter
public class OrderSearchResponse {

    private final Long orderId;
    private final DeliveryStatus deliveryStatus;
    private final OrderStatus orderStatus;
    private final List<OrderLineSearchResponse> orderLines = new ArrayList<>();

    @QueryProjection
    public OrderSearchResponse(Long orderId, DeliveryStatus deliveryStatus, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.deliveryStatus = deliveryStatus;
        this.orderStatus = orderStatus;
    }

    public void addOrderLines(OrderLineSearchResponse orderLine) {
        orderLines.add(orderLine);
    }

    @Getter
    public static class OrderLineSearchResponse {

        private final Long orderId;
        private final String itemName;
        private final int itemPrice;
        private final int orderQuantity;

        @QueryProjection
        public OrderLineSearchResponse(Long orderId,
                                       String itemName,
                                       int itemPrice,
                                       int orderQuantity
        ) {
            this.orderId = orderId;
            this.itemName = itemName;
            this.itemPrice = itemPrice;
            this.orderQuantity = orderQuantity;
        }

    }

}
