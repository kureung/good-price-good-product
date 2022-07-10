package kr.co.gpgp.domain.courier;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import kr.co.gpgp.domain.common.BaseEntity;
import kr.co.gpgp.domain.delivery.Delivery;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class CourierContainer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Courier courier;

    @ManyToOne(fetch = LAZY)
    private Delivery delivery;

    private CourierContainer(Courier courier, Delivery delivery) {
        this.courier = courier;
        this.delivery = delivery;
    }

    public static CourierContainer of(Courier courier, Delivery delivery) {
        return new CourierContainer(courier, delivery);
    }

}
