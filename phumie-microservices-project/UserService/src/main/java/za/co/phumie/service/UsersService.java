package za.co.phumie.service;

import org.springframework.stereotype.Service;
import za.co.phumie.dto.PhumieUserDto;
import za.co.phumie.dto.ResponseDto;
import za.co.phumie.exception.UserExistsException;
import za.co.phumie.mapper.UserMapper;
import za.co.phumie.model.PhumieUser;
import za.co.phumie.repository.UserRepository;
import java.time.LocalDateTime;

@Service
public class UsersService {
    private final UserRepository userRepository;

    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseDto save(PhumieUserDto userDto) {
        if(isUsernameOrEmailExists(userDto)){
            throw new UserExistsException(userDto.userEmail()+"/"+userDto.username());
        }
        var newUser = UserMapper.mapDtoToEntity(userDto);
        userRepository.save(newUser);
        // prepare response
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("Success");
        responseDto.setTimestamp(LocalDateTime.now());
        responseDto.setMicroserviceName("UsersService");
        return responseDto;
    }

    public boolean putUsername(String oldUsername, PhumieUserDto userDto) {
        var checkExistingUser = userRepository.findPhumieUserByUsername(oldUsername);
        var toBeUpdatedUser = userRepository.findPhumieUserByUsername(oldUsername);
        if(checkExistingUser == null){
            toBeUpdatedUser.setUsername(userDto.username());
            userRepository.save(toBeUpdatedUser);
        }else{
            throw new UserExistsException(userDto.username());
        }
        return true;
    }

    public void putPassword(PhumieUserDto dto){
        PhumieUser user = userRepository.getPhumieUserByUserEmail(dto.userEmail());
        userRepository.save(user);
    }

    private boolean isUsernameOrEmailExists(PhumieUserDto userDto) {
        return (userRepository.findPhumieUserByUserEmail(userDto.userEmail()) != null
                || userRepository.findPhumieUserByUsername(userDto.username()) != null);
    }
}
