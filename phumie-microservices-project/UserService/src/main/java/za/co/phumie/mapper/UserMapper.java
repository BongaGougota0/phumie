package za.co.phumie.mapper;

import za.co.phumie.dto.PhumieUserDto;
import za.co.phumie.model.PhumieUser;
import za.co.phumie.model.UserRole;

public class UserMapper {

    public static PhumieUserDto mapEntityToDto(PhumieUser user) {
        PhumieUserDto dto = new PhumieUserDto(user.getUsername(), user.getUserEmail(),
                "", user.getUserRole().name(), user.getAboutUser());
        return dto;
    }

    public static PhumieUser mapDtoToEntity(PhumieUserDto dto) {
        PhumieUser user = new PhumieUser();
        user.setUserEmail(dto.userEmail());
        user.setAboutUser(dto.aboutUser());
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setUserRole(UserRole.USER);
        return user;
    }
}
