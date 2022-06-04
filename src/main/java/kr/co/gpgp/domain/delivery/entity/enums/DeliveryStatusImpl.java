package kr.co.gpgp.domain.delivery.entity.enums;


import kr.co.gpgp.domain.delivery.exception.DeliveryStatusOverflowException;

public enum DeliveryStatusImpl implements DeliveryStatus {

    ACCEPT {
        public String get()  { return "결제완료"; }
        public DeliveryStatusImpl next() { return INSTRUCT; }
    },
    INSTRUCT {
        public String get()  { return "상품준비중"; }
        public DeliveryStatusImpl next() { return DEPARTURE; }
    },
    DEPARTURE {
        public String get()  { return "배송지시"; }
        public DeliveryStatusImpl next() { return FINAL_DELIVERY; }
    },
    FINAL_DELIVERY {
        public String get()  { return "배송중"; }
        public DeliveryStatusImpl next() { return NONE_TRACKING; }
    },
    NONE_TRACKING {
        public String get()  { return "배송완료"; }
        public DeliveryStatusImpl next() {
            throw new DeliveryStatusOverflowException("이미 완료된 배송입니다.");
        }
    };
}