package kr.co.gpgp.domain.delivery.exception;

public class DeliveryStatusAuthException extends RuntimeException  {
    public DeliveryStatusAuthException() {
    }

    public DeliveryStatusAuthException(String message) {
        super(message);
    }
}
