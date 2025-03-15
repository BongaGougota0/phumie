package za.co.phumie.PostsService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import za.co.phumie.PostsService.dto.ResponseDto;
import java.time.LocalDateTime;

@RestControllerAdvice
public class PostServiceErrorHandler {

    @ExceptionHandler(PostNotFound.class)
    public ResponseEntity<ResponseDto> handlePostNotFound(PostNotFound e) {
        ResponseDto responseDto = getResponseDto(e.getMessage());
        return new ResponseEntity(responseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyUsernamePostException.class)
    public ResponseEntity<ResponseDto> handleEmptyUsernamePostException(EmptyUsernamePostException e) {
        ResponseDto responseDto = getResponseDto(e.getMessage());
        return new ResponseEntity(responseDto, HttpStatus.BAD_REQUEST);
    }

    private static ResponseDto getResponseDto(String message) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage(message);
        responseDto.setTimestamp(LocalDateTime.now());
        return responseDto;
    }
}
