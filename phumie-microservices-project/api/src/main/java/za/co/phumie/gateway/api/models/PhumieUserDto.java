package za.co.phumie.gateway.api.models;

public record PhumieUserDto(String username, String userEmail,
                             String password, String userRole, String aboutUser) {
}
