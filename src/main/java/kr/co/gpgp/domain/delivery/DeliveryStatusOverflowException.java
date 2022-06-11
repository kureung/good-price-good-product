package kr.co.gpgp.domain.delivery;

public class DeliveryStatusOverflowException extends RuntimeException {

    public DeliveryStatusOverflowException() {
    }

    public DeliveryStatusOverflowException(String message) {
        super(message);
    }

}
