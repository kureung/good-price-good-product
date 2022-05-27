package kr.co.gpgp.domain.delivery.exception;

public class DeliveryStatusOverflowException extends RuntimeException {

    public DeliveryStatusOverflowException() {
    }

    public DeliveryStatusOverflowException(String message) {
        super(message);
    }

}
