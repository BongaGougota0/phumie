package za.phumie.shared.appdtos;

public record AuthenticationDto(String jwt, PhumieUserDto phumieUserDto) {
}
