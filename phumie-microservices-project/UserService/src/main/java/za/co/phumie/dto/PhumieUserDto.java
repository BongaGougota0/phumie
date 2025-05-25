package za.co.phumie.dto;

public record PhumieUserDto(Long userId, String username, String userEmail,
                            String password, String userRole, String aboutUser) {
}