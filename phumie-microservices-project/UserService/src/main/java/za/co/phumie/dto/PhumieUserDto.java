package za.co.phumie.dto;

public record PhumieUserDto(String username, String userEmail,
                            String password, String userRole, String aboutUser) {
}