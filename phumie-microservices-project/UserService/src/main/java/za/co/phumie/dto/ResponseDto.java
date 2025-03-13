package za.co.phumie.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ResponseDto {
    private String microserviceName;
    private String message;
    private String status;
    private LocalDateTime timestamp;
}
