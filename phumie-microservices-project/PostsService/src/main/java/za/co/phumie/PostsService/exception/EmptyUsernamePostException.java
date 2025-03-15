package za.co.phumie.PostsService.exception;

public class EmptyUsernamePostException extends RuntimeException {
    public EmptyUsernamePostException(String message) {
        super(message);
    }
}
