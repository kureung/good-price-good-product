package kr.co.gpgp.domain.delivery.entity.enums;


import kr.co.gpgp.domain.delivery.exception.DeliveryStatusOverflowException;

public enum StatusImpl implements Status {

    ACCEPT {
        public String get()  { return "결제완료"; }
        public StatusImpl next() { return INSTRUCT; }
    },
    INSTRUCT {
        public String get()  { return "상품준비중"; }
        public StatusImpl next() { return DEPARTURE; }
    },
    DEPARTURE {
        public String get()  { return "배송지시"; }
        public StatusImpl next() { return FINAL_DELIVERY; }
    },
    FINAL_DELIVERY {
        public String get()  { return "배송중"; }
        public StatusImpl next() { return NONE_TRACKING; }
    },
    NONE_TRACKING {
        public String get()  { return "배송완료"; }
        public StatusImpl next() {
            throw new DeliveryStatusOverflowException("이미 완료된 배송입니다.");
        }
    };
}