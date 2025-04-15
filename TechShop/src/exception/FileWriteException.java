package exception;

public class FileWriteException extends Exception {
	 public FileWriteException() {
	        super("Error writing to file.");
	    }

	    public FileWriteException(String message) {
	        super(message);
	    }

	    public FileWriteException(String message, Throwable cause) {
	        super(message, cause);
	    }
}
