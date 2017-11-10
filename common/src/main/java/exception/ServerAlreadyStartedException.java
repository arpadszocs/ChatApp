package exception;

public class ServerAlreadyStartedException extends RuntimeException{

    public ServerAlreadyStartedException() {
    }

    public ServerAlreadyStartedException(String message) {
        super(message);
    }

    public ServerAlreadyStartedException(String message, Throwable cause) {
        super(message, cause);
    }
}
