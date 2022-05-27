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

    private interface StatusUse {
        String get();
        Status next();
    }// |
    //  V
    protected enum Status implements  StatusUse {
        ACCEPT {
            public String get()  { return "결제완료"; }
            public Status next() { return INSTRUCT; }
        },
        INSTRUCT {
            public String get()  { return "상품준비중"; }
            public Status next() { return DEPARTURE; }
        },
        DEPARTURE {
            public String get()  { return "배송지시"; }
            public Status next() { return FINAL_DELIVERY; }
        },
        FINAL_DELIVERY {
            public String get()  { return "배송중"; }
            public Status next() { return NONE_TRACKING; }
        },
        NONE_TRACKING {
            public String get()  { return "배송완료"; }
            public Status next()  {
                throw new ArrayIndexOutOfBoundsException("이미 완료된 배송입니다.");
            }
        };

        private Status statusNext() {
            return this.next();
        }

        private String getValue() {
            return this.get();
        }

    }

    protected Delivery() {
        status = Status.ACCEPT;
    }

    public void next() {
        status = status.statusNext();
    }

    public String getStatus() {
        return status.getValue();
    }


}
