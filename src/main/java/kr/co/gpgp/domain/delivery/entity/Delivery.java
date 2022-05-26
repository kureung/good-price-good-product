package kr.co.gpgp.domain.delivery.entity;

import static javax.persistence.EnumType.STRING;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(STRING)
    private Status status;

    private enum Status {

        ACCEPT("결제완료"),          //결제완료
        INSTRUCT("상품준비중"),       //상품준비중
        DEPARTURE("배송지시"),       //배송지시
        FINAL_DELIVERY("배송중"),    //배송중
        NONE_TRACKING("배송완료");   //배송완료

        private int index = this.ordinal();
        private String val;

        Status(String val) {
            this.val = val;
        }

        private Status statusNext() {
            switch (this) {
                case ACCEPT:
                    return INSTRUCT;
                case INSTRUCT:
                    return DEPARTURE;
                case DEPARTURE:
                    return FINAL_DELIVERY;
                case FINAL_DELIVERY:
                    return NONE_TRACKING;
                default:
                    throw new EnumConstantNotPresentException(Status.class,"이미 완료된 배송입니다.");
            }
        }

        private String getValue() {
            return val;
        }

    }

    public Delivery() {
        status = Status.ACCEPT;
    }

    public void next() {
        status = status.statusNext();
    }

    public String getStatus() {
        return status.getValue();
    }


}
