package za.co.phumie.PostsService.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PostServiceErrorHandler {

    @ExceptionHandler(PostNotFound.class)
    @GetMapping("")
    public ResponseEntity<?> handlePostNotFound(PostNotFound e) {
        return ResponseEntity.ok(e.getMessage());
    }
}
