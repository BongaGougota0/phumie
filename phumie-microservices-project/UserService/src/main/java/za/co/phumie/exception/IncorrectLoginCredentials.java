package za.co.phumie.exception;

public class IncorrectLoginCredentials extends RuntimeException {
    public IncorrectLoginCredentials(String message) {
        super(message);
    }
}
