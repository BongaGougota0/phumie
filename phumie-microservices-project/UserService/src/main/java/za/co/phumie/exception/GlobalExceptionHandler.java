package za.co.phumie.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import za.co.phumie.dto.ResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler {
    public String USER_EXISTS_EXCEPTION = "User with %s already exists";
    public String USER_DOES_NOT_EXIST = "User with id %s exists";

    @ExceptionHandler({UserNotFound.class})
    public ResponseEntity<ResponseDto> userNotFound(String message){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage(String.format(USER_DOES_NOT_EXIST, message));
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<ResponseDto> userExists(String message){
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage(String.format(USER_EXISTS_EXCEPTION,message));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDto);
    }
}
