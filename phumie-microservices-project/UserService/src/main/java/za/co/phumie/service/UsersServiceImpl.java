package za.co.phumie.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import za.phumie.shared.appdtos.LoginCredentials;
import za.phumie.shared.appdtos.PhumieUserDto;
import za.phumie.shared.appdtos.ResponseDto;
import za.co.phumie.exception.IncorrectLoginCredentials;
import za.co.phumie.exception.UserExistsException;
import za.co.phumie.exception.UserNotFound;
import za.co.phumie.mapper.UserMapper;
import za.co.phumie.repository.UserRepository;
import za.phumie.shared.appmodels.PhumieUser;
import java.time.LocalDateTime;

@Service
public class UsersServiceImpl {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseDto save(PhumieUserDto userDto) {
        if(isUsernameOrEmailExists(userDto)){
            throw new UserExistsException(userDto.userEmail()+"/"+userDto.username());
        }
        var newUser = UserMapper.mapDtoToEntity(userDto);
        newUser.setPasswordHash(passwordEncoder.encode(userDto.password()));
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

    @Cacheable(value = "userDtosCache", unless = "#result == null")
    public Mono<PhumieUserDto> getUserById(Long userid) {
        return userRepository.findById(userid)
                .switchIfEmpty(Mono.error(
                        new UserNotFound(String.format("User with id %s not found", userid))
                ))
                .map(UserMapper::mapEntityToDto);
    }

    @Cacheable(value = "userIdsCache", unless = "#result == null")
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
    @Cacheable(value = "userEntitiesCache", key = "#userDto.username()")
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
            return true;
        }else{
            throw new UserExistsException(userDto.username());
        }
    }

    public void putPassword(PhumieUserDto dto){
        PhumieUser user = userRepository.getPhumieUserByUserEmail(dto.userEmail());
        userRepository.save(user);
    }

    private boolean isUsernameOrEmailExists(PhumieUserDto userDto) {
        return (userRepository.findPhumieUserByUserEmail(userDto.userEmail()) != null
                || userRepository.findPhumieUserByUsername(userDto.username()) != null);
    }

    public boolean authenticateUser(LoginCredentials loginCredentials){
        PhumieUser user = userRepository.findPhumieUserByUserEmail(loginCredentials.usernameEmail());

        if(user == null){
            throw new UserNotFound("No user found with the provided email or username");
        }

        if(passwordEncoder.matches(loginCredentials.password(), user.getPasswordHash())){
            return true;
        }else {
            throw new IncorrectLoginCredentials("Incorrect username or password");
        }
    }
}
