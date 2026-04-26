package za.co.phumie.service.usersint;

import za.phumie.shared.appdtos.PhumieUserDto;
import za.phumie.shared.appmodels.PhumieUser;

public interface IUsers {
    public PhumieUser getUserByEmail(String email);
    public Long getUserIdByEmail(String email);
    public String createNewUser(String firstName, String lastName, String email, String password);
    public String updateUserName(PhumieUserDto userDto);
    public String updateUserEmail(PhumieUserDto userDto);
    public PhumieUser getUserByEmailOrUsername(String emailOrUsername);
}
