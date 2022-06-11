package kr.co.gpgp.domain.order;

import kr.co.gpgp.domain.delivery.entity.enums.DeliveryStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderSearchCondition {

    private Long userId;
    private OrderStatus orderStatus;
    private DeliveryStatus deliveryStatus;

    @Builder
    private OrderSearchCondition(Long userId, OrderStatus orderStatus, DeliveryStatus deliveryStatus) {
        this.userId = userId;
        this.orderStatus = orderStatus;
        this.deliveryStatus = deliveryStatus;
    }

}
