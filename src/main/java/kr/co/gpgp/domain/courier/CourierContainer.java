package kr.co.gpgp.domain.courier;

import static javax.persistence.EnumType.STRING;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import kr.co.gpgp.domain.common.BaseEntity;
import kr.co.gpgp.domain.delivery.Delivery;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "courierContainer")
public class CourierContainer extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Courier courier;

    @ManyToOne
    private Delivery delivery;

    @Enumerated(STRING)
    private CourierStatus courierStatus;

    private CourierContainer(Courier courier, Delivery delivery) {
        this.courier = courier;
        this.delivery = delivery;
        courierStatus=CourierStatus.init();
    }

    public static CourierContainer of(Courier courier, Delivery delivery){
        return new CourierContainer(courier,delivery);
    }

}
