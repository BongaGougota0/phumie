package za.co.phumie.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import za.co.phumie.dto.PhumieUserDto;
import za.co.phumie.dto.ResponseDto;
import za.co.phumie.exception.UserExistsException;
import za.co.phumie.exception.UserNotFound;
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
        ResponseDto responseDto = prepareResponseDto();
        return responseDto;
    }

    private static ResponseDto prepareResponseDto() {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("Success");
        responseDto.setStatus(HttpStatus.OK.name());
        responseDto.setTimestamp(LocalDateTime.now());
        responseDto.setMicroserviceName("UsersService");
        return responseDto;
    }

    public PhumieUserDto getUserById(Long userid){
        PhumieUser user = userRepository.findById(userid).orElseThrow(
                () -> new UserNotFound(String.format("User with id %s not found", userid))
        );
        return UserMapper.mapEntityToDto(user);
    }

    public Long getUserByUsername(String username){
        return userRepository.findPhumieUserByUsername(username).getUserId();
    }

    /**
     * Updates user details in the system
     * @param userDto The user details to update
     * @return Response with operation status
     * @throws UserNotFound if user cannot be found
     */
    public ResponseDto putUserDetails(PhumieUserDto userDto) {
        if (userDto == null) {
            throw new IllegalArgumentException("User details cannot be null");
        }

        PhumieUser existingUser = getUserByEmailOrUsername(userDto);
        if (existingUser == null) {
            throw new UserNotFound("No user found with the provided email or username");
        }

        PhumieUser updatedUser = UserMapper.mapDtoToEntity(userDto);
        updatedUser.setUserId(existingUser.getUserId());
        userRepository.save(updatedUser);

        return prepareResponseDto();
    }

    /**
     * Retrieves a user by email or username
     * @param userDto User DTO containing either email or username
     * @return Found user or null if not found
     * @throws IllegalArgumentException if neither email nor username is provided
     */
    public PhumieUser getUserByEmailOrUsername(PhumieUserDto userDto) {
        if (userDto == null) {
            throw new IllegalArgumentException("User details cannot be null");
        }

        String email = userDto.userEmail();
        String username = userDto.username();

        if (email != null && !email.isEmpty() && userRepository.findPhumieUserByUserEmail(email) != null) {
            return userRepository.findPhumieUserByUserEmail(email);
        } else if (username != null && !username.isEmpty() && userRepository.findPhumieUserByUsername(username) != null) {
            return userRepository.findPhumieUserByUsername(username);
        } else {
            throw new IllegalArgumentException("Either email or username must be provided");
        }
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
