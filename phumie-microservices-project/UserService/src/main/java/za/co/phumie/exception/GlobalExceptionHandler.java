package za.co.phumie.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import za.co.phumie.dto.ResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFound.class})
    public ResponseEntity<ResponseDto> userNotFound(){
        ResponseDto responseDto = new ResponseDto();
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.NOT_FOUND);
    }
}
