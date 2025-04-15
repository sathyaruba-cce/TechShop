package exception;

public class CustomerNotFoundException extends Exception{
	public CustomerNotFoundException() {
        super("Customer not found.");
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
