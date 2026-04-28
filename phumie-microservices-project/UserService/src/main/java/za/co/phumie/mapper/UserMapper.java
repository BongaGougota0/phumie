package za.co.phumie.mapper;

import za.phumie.shared.appmodels.PhumieUser;
import za.phumie.shared.appmodels.UserRole;
import za.phumie.shared.mapper.ApplicationMapper;

public class UserMapper {

    public static ApplicationMapper.PhumieUserDto mapEntityToDto(PhumieUser user) {
        ApplicationMapper.PhumieUserDto dto = new ApplicationMapper.PhumieUserDto(user.getUserId(),
                user.getUsername(),
                user.getUserEmail(),
                user.getAboutUser(), user.getAvatarUrl(), "", user.getFollowerCount());
        return dto;
    }

    public static PhumieUser mapDtoToEntity(ApplicationMapper.PhumieUserDto dto) {
        PhumieUser user = new PhumieUser();
        user.setUserEmail(dto.userEmail());
        user.setAboutUser(dto.aboutUser());
        user.setUsername(dto.username());
        user.setPasswordHash(dto.passwordHash());
        user.setUserRole(UserRole.USER);
        return user;
    }
}
