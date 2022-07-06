package kr.co.gpgp.domain.courier;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import kr.co.gpgp.domain.common.BaseEntity;
import kr.co.gpgp.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Courier extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(STRING)
    private CourierArea courierArea;

    private Courier(User user, CourierArea courierArea) {
        this.user = user;
        this.courierArea = courierArea;
    }

    public static Courier of(User user, CourierArea courierArea) {
        return new Courier(user, courierArea);
    }

    public void updateCourierArea(CourierArea courierArea) {
        this.courierArea = courierArea;
    }

}
