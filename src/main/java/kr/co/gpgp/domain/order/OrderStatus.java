package kr.co.gpgp.domain.order;

public enum OrderStatus {
    ORDER("주문 상태"),
    CANCEL("주문 취소 상태"),
    ;

    private String message;

    OrderStatus(String message) {
        this.message = message;
    }
}
