package kr.co.gpgp.domain.orderline;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import kr.co.gpgp.domain.item.entity.Item;
import kr.co.gpgp.domain.order.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class OrderLine {

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
    private int count;

    private OrderLine(Item item, int price, int count) {
        this.item = item;
        this.price = price;
        this.count = count;
    }

    public static OrderLine of(Item item, int count) {
        item.minusStock(count);
        return new OrderLine(item, item.getPrice(), count);
    }


    public void cancel() {
        getItem().plusStock(count);
    }


    public int getTotalPrice() {
        return price * count;
    }

    public void designateOrder(Order order) {
        this.order = order;
    }

}
