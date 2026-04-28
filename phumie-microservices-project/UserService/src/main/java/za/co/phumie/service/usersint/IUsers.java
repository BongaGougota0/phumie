package za.co.phumie.service.usersint;

import za.phumie.shared.appmodels.PhumieUser;
import za.phumie.shared.mapper.ApplicationMapper;

public interface IUsers {
    public PhumieUser getUserByEmail(String email);
    public Long getUserIdByEmail(String email);
    public String createNewUser(String firstName, String lastName, String email, String password);
    public String updateUserName(ApplicationMapper.PhumieUserDto userDto);
    public String updateUserEmail(ApplicationMapper.PhumieUserDto userDto);
    public PhumieUser getUserByEmailOrUsername(String emailOrUsername);
}
