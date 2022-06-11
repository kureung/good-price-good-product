package kr.co.gpgp.repository.order;

import static java.util.stream.Collectors.toMap;
import static kr.co.gpgp.domain.address.entity.QAddress.address;
import static kr.co.gpgp.domain.delivery.entity.QDelivery.delivery;
import static kr.co.gpgp.domain.item.entity.QItem.item;
import static kr.co.gpgp.domain.order.entity.QOrder.order;
import static kr.co.gpgp.domain.orderline.entity.QOrderLine.orderLine;
import static kr.co.gpgp.domain.requirement.entity.QRequirement.requirement;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import kr.co.gpgp.domain.delivery.entity.enums.DeliveryStatus;
import kr.co.gpgp.domain.order.OrderSearchCondition;
import kr.co.gpgp.domain.order.entity.Order;
import kr.co.gpgp.domain.order.enums.OrderStatus;
import kr.co.gpgp.domain.orderline.entity.OrderLine;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private static Map<Long, Order> map;

    @Override
    public Page<Order> orderSearch(OrderSearchCondition condition, Pageable pageable) {

        List<Order> content = searchContent(condition, pageable);

        map = content.stream()
                .collect(toMap(Order::getId, Function.identity()));

        searchOrderLinesContent(map.keySet())
                .forEach(this::registerOrderLine);

        JPAQuery<Long> totalCount = getTotalCount(condition);
        return PageableExecutionUtils.getPage(content, pageable, totalCount::fetchOne);
    }

    private void registerOrderLine(OrderLine orderLine) {
        long orderId = orderLine.getOrderId();
        Order order = map.get(orderId);
        order.addOrderLine(orderLine);
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

    private List<Order> searchContent(OrderSearchCondition condition, Pageable pageable) {
        return queryFactory
                .selectFrom(order)
                .innerJoin(order.delivery, delivery)
                .innerJoin(delivery.requirement, requirement)
                .innerJoin(delivery.address, address)
                .where(
                        userIdEq(condition.getUserId()),
                        orderStatusEq(condition.getOrderStatus()),
                        deliveryStatusEq(condition.getDeliveryStatus())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private List<OrderLine> searchOrderLinesContent(Collection<Long> orderIds) {
        return queryFactory
                .selectFrom(orderLine)
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
