package za.co.phumie.service.usersint;

import za.co.phumie.dto.PhumieUserDto;
import za.co.phumie.model.PhumieUser;

public interface UsersInt {
    public PhumieUser getUserByEmail(String email);
    public Long getUserIdByEmail(String email);
    public String createNewUser(String firstName, String lastName, String email, String password);
    public String updateUserName(PhumieUserDto userDto);
    public String updateUserEmail(PhumieUserDto userDto);
}
