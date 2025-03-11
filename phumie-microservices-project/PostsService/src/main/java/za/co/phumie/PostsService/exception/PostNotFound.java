package za.co.phumie.PostsService.exception;

public class PostNotFound extends RuntimeException {
    public PostNotFound(String message) {
        super(message);
    }
}
