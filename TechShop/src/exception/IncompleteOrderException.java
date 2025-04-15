package exception;

public class IncompleteOrderException extends Exception{
	public IncompleteOrderException() {
        super("Order is incomplete or inconsistent.");
    }

    public IncompleteOrderException(String message) {
        super(message);
    }

    public IncompleteOrderException(String message, Throwable cause) {
        super(message, cause);
    }         
}
