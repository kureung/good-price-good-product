package kr.co.gpgp.repository.order;

import static java.util.stream.Collectors.toMap;
import static kr.co.gpgp.domain.delivery.QDelivery.delivery;
import static kr.co.gpgp.domain.item.QItem.item;
import static kr.co.gpgp.domain.order.QOrder.order;
import static kr.co.gpgp.domain.orderline.QOrderLine.orderLine;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import kr.co.gpgp.domain.delivery.DeliveryStatus;
import kr.co.gpgp.domain.order.OrderSearchCondition;
import kr.co.gpgp.domain.order.OrderSearchResponse;
import kr.co.gpgp.domain.order.OrderSearchResponse.OrderLineSearchResponse;
import kr.co.gpgp.domain.order.OrderStatus;
import kr.co.gpgp.domain.order.QOrderSearchResponse;
import kr.co.gpgp.domain.order.QOrderSearchResponse_OrderLineSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private static Map<Long, OrderSearchResponse> map;

    @Override
    public Page<OrderSearchResponse> orderSearch(OrderSearchCondition condition, Pageable pageable) {

        List<OrderSearchResponse> content = searchContent(condition, pageable);

        map = content.stream()
                .collect(toMap(OrderSearchResponse::getOrderId, Function.identity()));

        searchOrderLinesContent(map.keySet())
                .forEach(this::registerOrderLine);

        JPAQuery<Long> totalCount = getTotalCount(condition);
        return PageableExecutionUtils.getPage(content, pageable, totalCount::fetchOne);
    }

    private void registerOrderLine(OrderLineSearchResponse orderLineSearchResponse) {
        Long orderId = orderLineSearchResponse.getOrderId();
        OrderSearchResponse orderSearchResponse = map.get(orderId);
        orderSearchResponse.addOrderLines(orderLineSearchResponse);
    }

    private JPAQuery<Long> getTotalCount(OrderSearchCondition condition) {
        return queryFactory
                .select(order.count())
                .from(order)
                .where(
                        orderStatusEq(condition.getOrderStatus()),
                        deliveryStatusEq(condition.getDeliveryStatus())
                );
    }

    private List<OrderSearchResponse> searchContent(OrderSearchCondition condition, Pageable pageable) {
        return queryFactory
                .select(new QOrderSearchResponse(
                        order.id,
                        delivery.status,
                        order.orderStatus
                ))
                .from(order)
                .innerJoin(order.delivery, delivery)
                .where(
                        userIdEq(condition.getUserId()),
                        orderStatusEq(condition.getOrderStatus()),
                        deliveryStatusEq(condition.getDeliveryStatus())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private List<OrderLineSearchResponse> searchOrderLinesContent(Collection<Long> orderIds) {
        return queryFactory
                .select(new QOrderSearchResponse_OrderLineSearchResponse(
                        order.id,
                        item.info.name,
                        item.price,
                        orderLine.orderQuantity
                ))
                .from(orderLine)
                .innerJoin(orderLine.order, order)
                .innerJoin(orderLine.item, item)
                .where(
                        order.id.in(orderIds)
                )
                .fetch();
    }

    private BooleanExpression userIdEq(Long userId) {
        return userId!=null ? order.user.id.eq(userId):null;
    }

    private BooleanExpression orderStatusEq(OrderStatus orderStatus) {
        return orderStatus!=null ? order.orderStatus.eq(orderStatus):null;
    }

    private BooleanExpression deliveryStatusEq(DeliveryStatus deliveryStatus) {
        return deliveryStatus!=null ? order.delivery.status.eq(deliveryStatus):null;
    }

}
