package za.co.phumie.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import za.phumie.shared.appdtos.LoginCredentials;
import za.phumie.shared.appdtos.ResponseDto;
import za.co.phumie.exception.IncorrectLoginCredentials;
import za.co.phumie.exception.UserExistsException;
import za.co.phumie.exception.UserNotFound;
import za.co.phumie.repository.UserRepository;
import za.phumie.shared.appmodels.PhumieUser;
import za.phumie.shared.mapper.ApplicationMapper;
import java.time.LocalDateTime;

@Slf4j
@Service
public class UsersServiceImpl {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ─── Save ─────────────────────────────────────────────────────────────────

    public Mono<ResponseDto> save(ApplicationMapper.PhumieUserDto userDto) {
        return isUsernameOrEmailExists(userDto)
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new UserExistsException(
                                userDto.username() + "/" + userDto.username()
                        ));
                    }
                    PhumieUser newUser = ApplicationMapper.toUserEntity(userDto);
                    log.debug("New registration user: {}", userDto.userEmail());
                    newUser.setPasswordHash(passwordEncoder.encode(userDto.passwordHash()));
                    return userRepository.save(newUser);
                })
                .flatMap(saved -> prepareResponseDto());
    }

    // ─── Get by ID ────────────────────────────────────────────────────────────

    @Cacheable(value = "userDtosCache", unless = "#result == null")
    public Mono<ApplicationMapper.PhumieUserDto> getUserById(Long userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(
                        new UserNotFound(String.format("User with id %s not found", userId))
                ))
                .map(ApplicationMapper::toUserDto);
    }

    // ─── Get ID by Username ───────────────────────────────────────────────────

    @Cacheable(value = "userIdsCache", unless = "#result == null")
    public Mono<Long> getUserIdByUsername(String username) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(
                        new UserNotFound("User not found: " + username)
                ))
                .map(PhumieUser::getUserId);
    }

    // ─── Get by Email or Username ─────────────────────────────────────────────

    @Cacheable(value = "userEntitiesCache", key = "#userDto.username()")
    public Mono<PhumieUser> getUserByEmailOrUsername(ApplicationMapper.PhumieUserDto userDto) {
        if (userDto == null) {
            return Mono.error(new IllegalArgumentException("User details cannot be null"));
        }

        String email    = userDto.username();
        String username = userDto.username();

        if (email != null && !email.isEmpty()) {
            return userRepository.findByUserEmail(email)
                    .switchIfEmpty(Mono.error(
                            new UserNotFound("No user found with email: " + email)
                    ));
        } else if (username != null && !username.isEmpty()) {
            return userRepository.findByUsername(username)
                    .onErrorResume(e -> Mono.error(
                            new UserNotFound("No user found with username: " + username)
                    ));
        } else {
            return Mono.error(new IllegalArgumentException("Either email or username must be provided"));
        }
    }

    // ─── Update User Details ──────────────────────────────────────────────────

    public Mono<ResponseDto> putUserDetails(ApplicationMapper.PhumieUserDto userDto) {
        if (userDto == null) {
            return Mono.error(new IllegalArgumentException("User details cannot be null"));
        }

        return getUserByEmailOrUsername(userDto)
                .flatMap(existingUser -> {
                    PhumieUser updatedUser = ApplicationMapper.toUserEntity(userDto);
                    updatedUser.setUserId(existingUser.getUserId());
                    return userRepository.save(updatedUser);
                })
                .flatMap(saved -> prepareResponseDto());
    }

    // ─── Update Username ──────────────────────────────────────────────────────

    public Mono<Boolean> putUsername(String oldUsername, ApplicationMapper.PhumieUserDto userDto) {
        return userRepository.findByUsername(oldUsername)
                .switchIfEmpty(Mono.error(
                        new UserNotFound("User not found: " + oldUsername)
                ))
                .flatMap(existingUser ->
                        // check new username is not already taken
                        userRepository.existsByUsername(userDto.username())
                                .flatMap(taken -> {
                                    if (taken) {
                                        return Mono.error(new UserExistsException(userDto.username()));
                                    }
                                    existingUser.setUsername(userDto.username());
                                    userRepository.save(existingUser);
                                    return Mono.just(true);
                                })
                )
                .map(saved -> true);
    }

    // ─── Update Password ──────────────────────────────────────────────────────

    public Mono<ResponseDto> putPassword(ApplicationMapper.PhumieUserDto dto) {
        return userRepository.findByUserEmail(dto.username())
                .switchIfEmpty(Mono.error(
                        new UserNotFound("No user found with email: " + dto.username())
                ))
                .flatMap(user -> {
                    user.setPasswordHash(passwordEncoder.encode(null));
                    return userRepository.save(user);
                })
                .flatMap(saved -> prepareResponseDto());
    }

    // ─── Authenticate ─────────────────────────────────────────────────────────

    public Mono<ApplicationMapper.PhumieUserDto> authenticateUser(LoginCredentials loginCredentials) {
        return userRepository.findByUserEmail(loginCredentials.usernameEmail())
                .switchIfEmpty(Mono.error(
                        new UserNotFound("No user found with the provided email or username")
                ))
                .flatMap(user -> {
                    if (passwordEncoder.matches(loginCredentials.password(), user.getPasswordHash())) {
                        ApplicationMapper.PhumieUserDto userDto = ApplicationMapper.toUserDto(user);
                        return Mono.just(userDto);
                    }
                    return Mono.error(new IncorrectLoginCredentials("Incorrect username or password"));
                });
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────

    private Mono<Boolean> isUsernameOrEmailExists(ApplicationMapper.PhumieUserDto userDto) {
        return userRepository.existsByUserEmail(userDto.userEmail())
                .zipWith(userRepository.existsByUserEmail(userDto.userEmail()))
                .map(tuple -> tuple.getT1() || tuple.getT2()); // runs both checks in parallel
    }

    private static Mono<ResponseDto> prepareResponseDto() {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("Success");
        responseDto.setStatus(HttpStatus.OK.name());
        responseDto.setTimestamp(LocalDateTime.now());
        responseDto.setMicroserviceName("UsersService");
        return Mono.just(responseDto);
    }
}