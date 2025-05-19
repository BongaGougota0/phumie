package za.co.phumie.exception;

public class EmptyUsernamePostException extends RuntimeException {
    public EmptyUsernamePostException(String message) {
        super(message);
    }
}