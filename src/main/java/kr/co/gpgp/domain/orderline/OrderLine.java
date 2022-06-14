package kr.co.gpgp.domain.orderline;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import kr.co.gpgp.domain.common.BaseEntity;
import kr.co.gpgp.domain.item.Item;
import kr.co.gpgp.domain.order.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class OrderLine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int price;
    private int orderQuantity;

    private OrderLine(Item item, int price, int orderQuantity) {
        this.item = item;
        this.price = price;
        this.orderQuantity = orderQuantity;
    }

    public static OrderLine of(Item item, int orderQuantity) {
        item.minusStock(orderQuantity);
        return new OrderLine(item, item.getPrice(), orderQuantity);
    }

    public void cancel() {
        getItem().plusStock(orderQuantity);
    }

    public int getTotalPrice() {
        return price * orderQuantity;
    }

    public void designateOrder(Order order) {
        this.order = order;
    }

    public String getItemName() {
        return getItem().getName();
    }

    public String getItemCode() {
        return getItem().getCode();
    }

    public Long getOrderId() {
        return order.getId();
    }

}