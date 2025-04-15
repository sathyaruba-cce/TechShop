package exception;

public class PaymentFailedException extends Exception {
	public PaymentFailedException() {
        super("Payment failed or was declined.");
    }

    public PaymentFailedException(String message) {
        super(message);
    }

    public PaymentFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
