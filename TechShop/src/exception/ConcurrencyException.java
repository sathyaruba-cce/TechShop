package exception;

public class ConcurrencyException extends Exception{
	 public ConcurrencyException() {
	        super("Concurrency conflict detected.");
	    }

	    public ConcurrencyException(String message) {
	        super(message);
	    }

	    public ConcurrencyException(String message, Throwable cause) {
	        super(message, cause);
	    }
}
