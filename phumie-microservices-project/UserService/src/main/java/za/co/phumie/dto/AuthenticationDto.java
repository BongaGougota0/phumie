package za.co.phumie.dto;

public record AuthenticationDto(String jwt, PhumieUserDto phumieUserDto) {
}
