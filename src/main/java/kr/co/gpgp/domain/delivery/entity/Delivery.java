package kr.co.gpgp.domain.delivery.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Delivery /*extends BaseEntity*/ {

    public enum Status {
        ACCEPT,         //결제완료
        INSTRUCT,       //상품준비중
        DEPARTURE,      //배송지시
        FINAL_DELIVERY, //배송중
        NONE_TRACKING   //배송완료
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Status status;


    @OneToOne
    private Address address;
//   System.out.println(status.ordinal()); //enum 순서


    public void updateStatus() {
        // 업데이트 호출하면 자동으로 됨
        if (status == null) {
            status = Status.ACCEPT;
        } else if (status == Status.ACCEPT) {
            status = Status.INSTRUCT;
        } else if (status == Status.INSTRUCT) {
            status = Status.DEPARTURE;
        } else if (status == Status.DEPARTURE) {
            status = Status.FINAL_DELIVERY;
        } else if (status == Status.FINAL_DELIVERY) {
            status = Status.NONE_TRACKING;
        }
    }


}
