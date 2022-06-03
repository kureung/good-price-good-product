package kr.co.gpgp.domain.delivery.entity;

import static javax.persistence.EnumType.STRING;

import com.querydsl.core.types.Order;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import kr.co.gpgp.domain.delivery.entity.enums.StatusImpl;
import kr.co.gpgp.domain.user.entity.User;
import lombok.Getter;

@Entity
@Getter
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requirement_id")
    private Requirement requirement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;


    @Enumerated(STRING)
    private StatusImpl status;

    public Delivery() {
        status = StatusImpl.ACCEPT;
    }

    public void next() {
        status = status.next();
    }

    public String getStatus() {
        return status.get();
    }
}
