package exception;

public class InvalidDataException extends Exception {
	public InvalidDataException() {
        super("Invalid data provided.");
    }

    public InvalidDataException(String message) {
        super(message);
    }
}
