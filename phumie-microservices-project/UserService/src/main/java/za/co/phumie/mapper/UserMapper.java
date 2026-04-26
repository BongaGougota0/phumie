package za.co.phumie.mapper;

import za.phumie.shared.appdtos.PhumieUserDto;
import za.phumie.shared.appmodels.PhumieUser;
import za.phumie.shared.appmodels.UserRole;

public class UserMapper {

    public static PhumieUserDto mapEntityToDto(PhumieUser user) {
        PhumieUserDto dto = new PhumieUserDto(user.getUserId(),
                user.getUsername(),
                user.getUserEmail(),
                "", user.getUserRole().name(),
                user.getAboutUser());
        return dto;
    }

    public static PhumieUser mapDtoToEntity(PhumieUserDto dto) {
        PhumieUser user = new PhumieUser();
        user.setUserEmail(dto.userEmail());
        user.setAboutUser(dto.aboutUser());
        user.setUsername(dto.username());
        user.setPasswordHash(dto.password());
        user.setUserRole(UserRole.USER);
        return user;
    }
}
